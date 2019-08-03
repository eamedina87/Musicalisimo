package ec.erickmedina.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ec.erickmedina.data.local.datasource.LocalDataSource
import ec.erickmedina.data.remote.datasource.RemoteDataSource
import ec.erickmedina.data.util.mapToDBEntity
import ec.erickmedina.data.util.mapToModel
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
                albumList.add(entity.mapToModel())
            }
            albumList
        }
    }

    override suspend fun saveAlbum(album: AlbumModel): AlbumModel? {
        val result = localDataSource.saveAlbum(album.mapToDBEntity())
        return if (result) {
            album
        } else {
            null
        }
    }

    override suspend fun deleteAlbum(album: AlbumModel): AlbumModel? {
        val result = localDataSource.deleteAlbum(album.mapToDBEntity())
        return if (result) {
            album
        } else {
            null
        }
    }

    override suspend fun searchArtistWithInput(input: String): ArrayList<ArtistModel> {
        val artists = ArrayList<ArtistModel>()
        val artistMatches = remoteDataSource.searchArtistAsync(input)
        artistMatches?.artist?.forEach {
            artists.add(it.mapToModel())
        }
        return artists
    }

    override suspend fun getTopAlbumsForArtist(artisId: String): ArrayList<TopAlbumModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getAlbumInfoForId(albumId: String): AlbumModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}