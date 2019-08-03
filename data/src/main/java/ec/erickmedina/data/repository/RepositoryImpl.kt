package ec.erickmedina.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ec.erickmedina.data.local.datasource.LocalDataSource
import ec.erickmedina.data.remote.datasource.RemoteDataSource
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.AlbumFilter
import org.modelmapper.ModelMapper

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource) : Repository {

    override suspend fun getLocalAlbums(filter: AlbumFilter): LiveData<ArrayList<AlbumModel>> {
        return Transformations.map(localDataSource.getSavedAlbums()){
            list ->
            val albumList = ArrayList<AlbumModel>()
            list?.forEach { entity ->
                val album : AlbumModel = ModelMapper().map(entity, AlbumModel::class.java)
                albumList.add(album)
            }
            albumList
        }
    }

    override suspend fun saveAlbum(album: AlbumModel): AlbumModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAlbum(album: AlbumModel): AlbumModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun searchArtistWithInput(input: String): ArrayList<ArtistModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTopAlbumsForArtist(artisId: String): ArrayList<TopAlbumModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getAlbumInfoForId(albumId: String): AlbumModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}