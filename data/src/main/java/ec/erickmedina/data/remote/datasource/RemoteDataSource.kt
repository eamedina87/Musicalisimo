package ec.erickmedina.data.remote.datasource

import ec.erickmedina.data.entity.LastFmResponses

interface RemoteDataSource {

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