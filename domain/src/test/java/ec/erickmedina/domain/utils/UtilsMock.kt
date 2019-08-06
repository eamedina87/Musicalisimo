package ec.erickmedina.data.utils

import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.ImageModel
import ec.erickmedina.domain.models.TopAlbumModel
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

        fun getMockedTopAlbumList(): ArrayList<TopAlbumModel> =
            arrayListOf(getTopAlbumOne(), getTopAlbumTwo(), getTopAlbumThree())

        fun getTopAlbumOne():TopAlbumModel =
            TopAlbumModel("OASIS", 1000, "", "", getArtistOne(), getImageList())

        fun getTopAlbumTwo():TopAlbumModel =
            TopAlbumModel("Barrio Fino", 10000, "", "", getArtistTwo(), getImageList())

        fun getTopAlbumThree():TopAlbumModel =
            TopAlbumModel("Grandes Exitos", 1000000, "", "", getArtistThree(), getImageList())

        fun getImageList():ArrayList<ImageModel> =
            arrayListOf(getImageOne(), getImageTwo(), getImageThree())

        private fun getImageOne(): ImageModel =
            ImageModel("http://", "small")

        private fun getImageTwo(): ImageModel =
            ImageModel("http://", "large")

        private fun getImageThree(): ImageModel =
            ImageModel("http://", "medium")



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