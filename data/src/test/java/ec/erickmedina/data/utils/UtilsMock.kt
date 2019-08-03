package ec.erickmedina.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.local.database.entity.DatabaseEntities
import ec.erickmedina.data.util.mapToModel
import ec.erickmedina.domain.models.AlbumModel
import kotlinx.coroutines.CompletableDeferred
import retrofit2.Response
import java.util.*

class UtilsMock {

    companion object {

        fun getSearchArtistDeferredResponse(): CompletableDeferred<Response<LastFmResponses.SearchArtistResponse>> {
            val data = getSearchArtistResponseEntity()
            val response = Response.success(data)
            return CompletableDeferred(response)
        }

        fun getTopAlbumsDeferredResponse(): CompletableDeferred<Response<LastFmResponses.TopAlbumResponse>> {
            val data = getTopAlbumResponseEntity()
            val response = Response.success(data)
            return CompletableDeferred(response)
        }

        fun getAlbumInfoDeferredResponse(): CompletableDeferred<Response<LastFmResponses.AlbumInfoResponse>> {
            val data = getAlbumInfoResponseEntity()
            val response = Response.success(data)
            return CompletableDeferred(response)
        }

        fun getRemoteSourceArtistMatches() : LastFmResponses.SearchArtistMatches {
            val data = getSearchArtistResponseEntity()
            return data.results.artistmatches
        }

        fun getRemoteSourceTopAlbums(): LastFmResponses.TopAlbums {
            val data = getTopAlbumResponseEntity()
            return data.topalbums
        }

        fun getRemoteSourceAlbumInfo(): LastFmResponses.Album {
            val data = getAlbumInfoResponseEntity()
            return data.album
        }

        fun getMockedDatabaseAlbumList() : List<DatabaseEntities.AlbumEntity> {
            return listOf(getAlbumOne(), getAlbumTwo(), getAlbumThree())
        }

        fun getMockedLiveDatabaseAlbumList() : LiveData<List<DatabaseEntities.AlbumEntity>> {
            return MutableLiveData(getMockedDatabaseAlbumList())
        }

        fun getAlbumThreeModel(): AlbumModel =
            getAlbumThree().mapToModel()

        fun getAlbumTwoModel(): AlbumModel =
            getAlbumTwo().mapToModel()

        fun getAlbumOneModel(): AlbumModel =
            getAlbumOne().mapToModel()

        fun getAlbumThree(): DatabaseEntities.AlbumEntity =
            getAlbumWithName("album_three.json")

        fun getAlbumTwo(): DatabaseEntities.AlbumEntity =
            getAlbumWithName("album_two.json")

        fun getAlbumOne(): DatabaseEntities.AlbumEntity =
            getAlbumWithName("album_one.json")

        private fun getSearchArtistResponseJson(): String {
            val stream = this::class.java.classLoader.getResourceAsStream("lastfm_artist_search_response.json")
            return convertStreamToString(stream)
        }

        private fun getTopAlbumsResponseJson(): String {
            val stream = this::class.java.classLoader.getResourceAsStream("lastfm_top_albums_response.json")
            return convertStreamToString(stream)
        }

        private fun getAlbumInfoResponseJson(): String {
            val stream = this::class.java.classLoader.getResourceAsStream("lastfm_album_info_response.json")
            return convertStreamToString(stream)
        }

        private fun convertStreamToString(ins:java.io.InputStream ) : String {
            val s = Scanner(ins).useDelimiter("\\A")
            return if (s.hasNext()) s.next() else ""
        }

        private fun getAlbumWithName(name: String): DatabaseEntities.AlbumEntity {
            val stream = this::class.java.classLoader.getResourceAsStream(name)
            val json = convertStreamToString(stream)
            val data = Gson().fromJson(json, LastFmResponses.AlbumInfoResponse::class.java)
            return DatabaseEntities.AlbumEntity(data.album)
        }

        private fun getSearchArtistResponseEntity(): LastFmResponses.SearchArtistResponse {
            val json = getSearchArtistResponseJson()
            val data = Gson().fromJson(json, LastFmResponses.SearchArtistResponse::class.java)
            return data
        }

        private fun getAlbumInfoResponseEntity(): LastFmResponses.AlbumInfoResponse {
            val json = getAlbumInfoResponseJson()
            val data = Gson().fromJson(json, LastFmResponses.AlbumInfoResponse::class.java)
            return data
        }

        private fun getTopAlbumResponseEntity(): LastFmResponses.TopAlbumResponse {
            val json = getTopAlbumsResponseJson()
            val data = Gson().fromJson(json, LastFmResponses.TopAlbumResponse::class.java)
            return data
        }


    }




}