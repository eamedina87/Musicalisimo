package ec.erickmedina.data.util

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

    }




}