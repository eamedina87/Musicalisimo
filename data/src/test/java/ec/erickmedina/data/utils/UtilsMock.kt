package ec.erickmedina.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.local.database.entity.DatabaseEntities
import java.util.*

class UtilsMock {

    companion object {

        fun getSearchArtistResponseJson(): String {
            val stream = this::class.java.classLoader.getResourceAsStream("lastfm_artist_search_response.json")
            return convertStreamToString(stream)
        }

        fun getTopAlbumsResponseJson(): String {
            val stream = this::class.java.classLoader.getResourceAsStream("lastfm_top_albums_response.json")
            return convertStreamToString(stream)
        }

        fun getAlbumInfoResponseJson(): String {
            val stream = this::class.java.classLoader.getResourceAsStream("lastfm_album_info_response.json")
            return convertStreamToString(stream)
        }

        private fun convertStreamToString(ins:java.io.InputStream ) : String {
            val s = Scanner(ins).useDelimiter("\\A")
            return if (s.hasNext()) s.next() else ""
        }

        fun getMockedDatabaseAlbumList() : List<DatabaseEntities.AlbumEntity> {
            return listOf(getAlbumOne(), getAlbumTwo(), getAlbumThree())
        }

        fun getMockedLiveDatabaseAlbumList() : LiveData<List<DatabaseEntities.AlbumEntity>> {
            return MutableLiveData(getMockedDatabaseAlbumList())
        }

        fun getAlbumThree(): DatabaseEntities.AlbumEntity =
            getAlbumWithName("album_three.json")

        fun getAlbumTwo(): DatabaseEntities.AlbumEntity =
            getAlbumWithName("album_two.json")

        fun getAlbumOne(): DatabaseEntities.AlbumEntity =
            getAlbumWithName("album_one.json")

        private fun getAlbumWithName(name: String): DatabaseEntities.AlbumEntity {
            val stream = this::class.java.classLoader.getResourceAsStream(name)
            val json = convertStreamToString(stream)
            val data = Gson().fromJson(json, LastFmResponses.AlbumInfoResponse::class.java)
            return DatabaseEntities.AlbumEntity(data.album)
        }

    }




}