package ec.erickmedina.data.remote.datasource

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import ec.erickmedina.data.BuildConfig
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.remote.client.LastFmApiConstants
import ec.erickmedina.data.remote.client.RemoteClient
import ec.erickmedina.data.remote.datasource.artist.ArtistDataSourceFactory
import ec.erickmedina.data.remote.datasource.top_albums.TopAlbumsSourceFactory
import ec.erickmedina.data.util.handleErrors
import ec.erickmedina.data.util.mapToModel
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.Listing
import ec.erickmedina.domain.models.TopAlbumModel


class RemoteDataSourceImpl(private val remoteClient: RemoteClient) : RemoteDataSource {

    private val artistErrorMessage = "Artist Search Response is null"
    private val topAlbumsErrorMessage = "Top Albums Response is null"
    private val albumInfoErrorMessage = "Album Info Response is null"

    private val caller =  remoteClient.getRemoteCaller()

    override fun searchArtist(artist: String, page: String?): Listing<ArtistModel> {
        val sourceFactory = ArtistDataSourceFactory(remoteClient,
            artist, artistErrorMessage)
        val dataLive = sourceFactory.map { it.mapToModel() }.toLiveData(
            pageSize = LastFmApiConstants.param_search_limit_default.toInt())
        val dataState = Transformations.switchMap(sourceFactory.dataSource) {
            it.getDataState()
        }
        return Listing(dataLive, dataState)
    }

    override fun getTopAlbums(artist: String, page: String?): Listing<TopAlbumModel> {
        val sourceFactory = TopAlbumsSourceFactory(remoteClient,
            artist, artistErrorMessage)
        val dataLive = sourceFactory.map { it.mapToModel() }.toLiveData(
            pageSize = LastFmApiConstants.param_albums_limit_default.toInt())
        val dataState = Transformations.switchMap(sourceFactory.dataSource) {
            it.getDataState()
        }
        return Listing(dataLive, dataState)
    }

    override suspend fun searchArtistAsync(
        artist: String,
        page: String?)
            : LastFmResponses.SearchArtistMatches {
        val response = caller.searchArtistAsync(artist = artist, apiKey = BuildConfig.API_KEY,
                page = page ?: LastFmApiConstants.param_page_default).
                await().handleErrors(artistErrorMessage)
        return response.results.artistmatches
    }

    override suspend fun getTopAlbumsForArtistIdAsync(
        artistId: String,
        page: String?
    ): LastFmResponses.TopAlbums {
        val response = caller.getTopAlbumsForArtistIdAsync(artistId = artistId, apiKey = BuildConfig.API_KEY,
                page = page ?: LastFmApiConstants.param_page_default)
                .await().handleErrors(topAlbumsErrorMessage)
        return response.topalbums
    }

    override suspend fun getAlbumInfoForId(
        albumId: String,
        lang: String): LastFmResponses.Album {
        val response =
            caller.getAlbumInfoForIdAsync(albumId = albumId, lang = lang, apiKey = BuildConfig.API_KEY).
                await().handleErrors(albumInfoErrorMessage)
        return response.album
    }

}