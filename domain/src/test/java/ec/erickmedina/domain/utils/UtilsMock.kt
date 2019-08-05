package ec.erickmedina.data.utils

import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.ImageModel
import java.util.*
import kotlin.collections.ArrayList

class UtilsMock {

    companion object {

        fun getMockedArtistList() : ArrayList<ArtistModel> =
            arrayListOf(getArtistOne(), getArtistTwo(), getArtistThree())

        fun getArtistOne():ArtistModel =
            ArtistModel("JBalvin", 15000, "mbid", arrayListOf(ImageModel("", "small")))

        fun getArtistTwo():ArtistModel =
            ArtistModel("Daddy Yankee", 20000, "mbid", arrayListOf(ImageModel("", "small")))

        fun getArtistThree():ArtistModel =
            ArtistModel("Julio Jaramillo", 150000, "mbid", arrayListOf(ImageModel("", "small")))

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



    }




}