package ec.erickmedina.data.util

import com.google.gson.Gson
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.local.database.entity.DatabaseEntities
import java.util.*

class MockUtils {
    companion object {

        fun getMockedAlbum() : DatabaseEntities.AlbumEntity {
            val json = getAlbumInfoResponseJson()
            val data = Gson().fromJson(json, LastFmResponses.AlbumInfoResponse::class.java)
            return DatabaseEntities.AlbumEntity(data.album)
        }

        fun getAlbumInfoResponseJson(): String {
            val stream = this::class.java.classLoader.getResourceAsStream("lastfm_album_info_response.json")
            return convertStreamToString(stream)
        }

        private fun convertStreamToString(ins:java.io.InputStream ) : String {
            val s = Scanner(ins).useDelimiter("\\A")
            return if (s.hasNext()) s.next() else ""
        }
    }
}