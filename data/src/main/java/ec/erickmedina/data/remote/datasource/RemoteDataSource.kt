package ec.erickmedina.data.remote.datasource

import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.Listing
import ec.erickmedina.domain.models.TopAlbumModel

interface RemoteDataSource {

    fun searchArtist(
        artist: String,
        page: String? = null
    ) : Listing<ArtistModel>

    suspend fun searchArtistAsync(
        artist: String,
         page: String? = null
    ) : LastFmResponses.SearchArtistMatches

    fun getTopAlbums(
        artist: String,
        page: String? = null
    ) : Listing<TopAlbumModel>

    suspend fun getTopAlbumsForArtistIdAsync(
        artistId: String,
        page: String? = null) : LastFmResponses.TopAlbums

    suspend fun getAlbumInfoForId(
        albumId: String,
        lang: String) : LastFmResponses.Album

}