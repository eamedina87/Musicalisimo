package ec.erickmedina.domain.repository

import androidx.lifecycle.LiveData
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.states.AlbumFilter

interface Repository {

    suspend fun getLocalAlbums(filter:AlbumFilter):LiveData<ArrayList<AlbumModel>>
    suspend fun saveAlbum(album:AlbumModel): AlbumModel?
    suspend fun deleteAlbum(album:AlbumModel): AlbumModel?

    suspend fun searchArtistWithInput(input:String):ArrayList<ArtistModel>
    suspend fun getTopAlbumsForArtist(artisId:String):ArrayList<TopAlbumModel>
    suspend fun getAlbumInfoForId(albumId:String):AlbumModel

}