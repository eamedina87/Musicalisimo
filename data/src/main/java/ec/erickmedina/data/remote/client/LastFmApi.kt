package ec.erickmedina.data.remote.client

import ec.erickmedina.data.entity.LastFmResponses
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi {

    @GET(".")
    fun searchArtistAsync(
        @Query(LastFmApiConstants.param_method) method: String = LastFmApiConstants.param_method_artist_search,
        @Query(LastFmApiConstants.param_artist) artist: String,
        @Query(LastFmApiConstants.param_api_key) apiKey: String,
        @Query(LastFmApiConstants.param_format) format: String = LastFmApiConstants.param_format_json,
        @Query(LastFmApiConstants.param_limit) limit: String = LastFmApiConstants.param_search_limit_default,
        @Query(LastFmApiConstants.param_page) page: String = LastFmApiConstants.param_page_default
    ) : Deferred<Response<LastFmResponses.SearchArtistResponse>>

    @GET(".")
    fun searchArtists(
        @Query(LastFmApiConstants.param_method) method: String = LastFmApiConstants.param_method_artist_search,
        @Query(LastFmApiConstants.param_artist) artist: String,
        @Query(LastFmApiConstants.param_api_key) apiKey: String,
        @Query(LastFmApiConstants.param_format) format: String = LastFmApiConstants.param_format_json,
        @Query(LastFmApiConstants.param_limit) limit: String = LastFmApiConstants.param_search_limit_default,
        @Query(LastFmApiConstants.param_page) page: String = LastFmApiConstants.param_page_default
    ) : Call<LastFmResponses.SearchArtistResponse>

    @GET(".")
    fun getTopAlbumsForArtistIdAsync(
        @Query(LastFmApiConstants.param_method) method: String = LastFmApiConstants.param_method_artist_top_albums,
        @Query(LastFmApiConstants.param_artist) artistId: String,
        @Query(LastFmApiConstants.param_api_key) apiKey: String,
        @Query(LastFmApiConstants.param_format) format: String = LastFmApiConstants.param_format_json,
        @Query(LastFmApiConstants.param_limit) limit: String = LastFmApiConstants.param_albums_limit_default,
        @Query(LastFmApiConstants.param_page) page: String = LastFmApiConstants.param_page_default
    ) : Deferred<Response<LastFmResponses.TopAlbumResponse>>

    @GET(".")
    fun getTopAlbumsForArtist(
        @Query(LastFmApiConstants.param_method) method: String = LastFmApiConstants.param_method_artist_top_albums,
        @Query(LastFmApiConstants.param_artist) artist: String,
        @Query(LastFmApiConstants.param_api_key) apiKey: String,
        @Query(LastFmApiConstants.param_format) format: String = LastFmApiConstants.param_format_json,
        @Query(LastFmApiConstants.param_limit) limit: String = LastFmApiConstants.param_albums_limit_default,
        @Query(LastFmApiConstants.param_page) page: String = LastFmApiConstants.param_page_default
    ) : Call<LastFmResponses.TopAlbumResponse>

    @GET(".")
    fun getAlbumInfoForIdAsync(
        @Query(LastFmApiConstants.param_method) method: String = LastFmApiConstants.param_method_album_info,
        @Query(LastFmApiConstants.param_id) albumId: String,
        @Query(LastFmApiConstants.param_api_key) apiKey: String,
        @Query(LastFmApiConstants.param_format) format: String = LastFmApiConstants.param_format_json,
        @Query(LastFmApiConstants.param_lang) lang: String
    ) : Deferred<Response<LastFmResponses.AlbumInfoResponse>>

}