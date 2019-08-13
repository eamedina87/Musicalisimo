package ec.erickmedina.domain.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ec.erickmedina.domain.models.*
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

        fun getMockedLiveAlbumList(): LiveData<ArrayList<AlbumModel>> =
                MutableLiveData(getMockedAlbumList())

        fun getMockedAlbumList(): ArrayList<AlbumModel> =
            arrayListOf(getAlbumOne(), getAlbumTwo(), getAlbumThree())

        fun getAlbumEmpty(): AlbumModel =
            AlbumModel("", "", 1, "mbid", getImageList(), 1000,
                1000, arrayListOf(), getTagList(), "2019-02", "", "", false)

        fun getAlbumOne():AlbumModel =
            AlbumModel("OASIS", "JBalvin", 1, "mbid", getImageList(), 1000,
                1000, getTrackList(), getTagList(), "2019-02", "", "", false)

        fun getAlbumTwo():AlbumModel =
            AlbumModel("Barrio Fino", "Daddy Yankee", 2, "mbid", getImageList(), 10000,
                10000, getTrackList(), getTagList(), "2004-04", "", "", false)

        fun getAlbumThree():AlbumModel =
            AlbumModel("Grandes Exitos", "Julio Jaramillo", 3, "mbid", getImageList(), 100000,
                100000, getTrackList(), getTagList(), "1985-02", "", "", false)

        fun getImageList():ArrayList<ImageModel> =
            arrayListOf(getImageOne(), getImageTwo(), getImageThree())

        private fun getImageOne(): ImageModel =
            ImageModel("http://", "small")

        private fun getImageTwo(): ImageModel =
            ImageModel("http://", "large")

        private fun getImageThree(): ImageModel =
            ImageModel("http://", "medium")

        private fun getTagList(): ArrayList<TagModel> =
            arrayListOf(getTagOne(), getTagTwo(), getTagThree())


        fun getTagOne() : TagModel =
            TagModel("reggaeton")

        fun getTagTwo() : TagModel =
            TagModel("latin")

        fun getTagThree() : TagModel =
            TagModel("pasillo")

        private fun getTrackList(): ArrayList<TrackModel> =
            arrayListOf(getTrackOne(), getTrackTwo(), getTrackThree())

        private fun getTrackOne(): TrackModel =
            TrackModel("Song One", 250, 3)

        private fun getTrackTwo(): TrackModel =
            TrackModel("Song Two", 150, 2)

        private fun getTrackThree(): TrackModel =
            TrackModel("Song Three", 200, 1)




    }




}