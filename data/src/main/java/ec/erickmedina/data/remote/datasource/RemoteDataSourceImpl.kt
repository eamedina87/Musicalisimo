package ec.erickmedina.data.remote.datasource

import com.google.gson.Gson
import ec.erickmedina.data.BuildConfig
import ec.erickmedina.data.entity.ErrorEntity
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.remote.client.RemoteClient

class RemoteDataSourceImpl(private val remoteClient: RemoteClient) : RemoteDataSource {

    private val caller = remoteClient.getRemoteCaller()

    override suspend fun searchArtistAsync(
        artist: String,
        page: String?)
            : LastFmResponses.SearchArtistMatches {

        var artistMatches = LastFmResponses.SearchArtistMatches(arrayOf())
        val response = if (page != null) {
            caller.searchArtistAsync(artist = artist, apiKey = BuildConfig.API_KEY, page = page).await()
        } else {
            caller.searchArtistAsync(artist = artist, apiKey = BuildConfig.API_KEY).await()
        }
        response?.let {
            if (response.isSuccessful) {
                val artistResponse = it.body()
                if (artistResponse != null) {
                    artistMatches = artistResponse.results.artistmatches
                } else {
                    throw Exception("Artist Search Response is null")
                }
            } else {
                val message = it.errorBody()?.charStream()?.readText()
                val mError = Gson().fromJson<ErrorEntity>(message, ErrorEntity::class.java)
                throw Exception(mError.message)
            }
        }
        return artistMatches
    }

    override suspend fun getTopAlbumsForArtistIdAsync(
        artistId: String,
        page: String?
    ): LastFmResponses.TopAlbums {

        var topAlbums = LastFmResponses.TopAlbums(arrayOf())
        val response = if (page != null) {
            caller.getTopAlbumsForArtistIdAsync(artistId = artistId, apiKey = BuildConfig.API_KEY, page = page).await()
        } else {
            caller.getTopAlbumsForArtistIdAsync(artistId = artistId, apiKey = BuildConfig.API_KEY).await()
        }
        response?.let {
            if (response.isSuccessful) {
                val topAlbumsResponse = it.body()
                if (topAlbumsResponse != null) {
                    topAlbums = topAlbumsResponse.topalbums
                } else {
                    throw Exception("Top Albums Response is null")
                }
            } else {
                val message = it.errorBody()?.charStream()?.readText()
                val mError = Gson().fromJson<ErrorEntity>(message, ErrorEntity::class.java)
                throw Exception(mError.message)
            }
        }
        return topAlbums
    }

    override suspend fun getAlbumInfoForId(
        albumId: String,
        lang: String): LastFmResponses.Album {

        var album = LastFmResponses.Album("", "", " ","", arrayOf(),
            0, 0, LastFmResponses.Tracks(arrayOf()), LastFmResponses.Tags(arrayOf()),
            LastFmResponses.Wiki("", "", ""))
        val response =
            caller.getAlbumInfoForIdAsync(albumId = albumId, lang = lang, apiKey = BuildConfig.API_KEY).await()
        response?.let {
            if (response.isSuccessful) {
                val albumInfoResponse = it.body()
                if (albumInfoResponse != null) {
                    album = albumInfoResponse.album
                } else {
                    throw Exception("Album Info Response is null")
                }
            } else {
                val message = it.errorBody()?.charStream()?.readText()
                val mError = Gson().fromJson<ErrorEntity>(message, ErrorEntity::class.java)
                throw Exception(mError.message)
            }
        }
        return album
    }

}