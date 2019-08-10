package ec.erickmedina.data.remote.datasource

import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.Listing

interface RemoteDataSource {

    fun searchArtist(
        artist: String,
        page: String? = null
    ) : Listing<ArtistModel>

    suspend fun searchArtistAsync(
        artist: String,
         page: String? = null
    ) : LastFmResponses.SearchArtistMatches

    suspend fun getTopAlbumsForArtistIdAsync(
        artistId: String,
        page: String? = null) : LastFmResponses.TopAlbums

    suspend fun getAlbumInfoForId(
        albumId: String,
        lang: String) : LastFmResponses.Album

}