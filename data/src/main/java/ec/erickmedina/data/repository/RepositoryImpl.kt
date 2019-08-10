package ec.erickmedina.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ec.erickmedina.data.local.datasource.LocalDataSource
import ec.erickmedina.data.remote.datasource.RemoteDataSource
import ec.erickmedina.data.util.mapToDBEntity
import ec.erickmedina.data.util.mapToModel
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.Listing
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.AlbumFilter
import java.util.*
import kotlin.collections.ArrayList

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

    override fun searchArtist(artist: String, page: String?) : Listing<ArtistModel> =
        remoteDataSource.searchArtist(artist, page)


    override fun getTopAlbumsFor(artist: String, page: String?): Listing<TopAlbumModel> =
        remoteDataSource.getTopAlbums(artist, page)

    override suspend fun searchArtistWithInput(input: String): ArrayList<ArtistModel> {
        val artists = ArrayList<ArtistModel>()
        val artistMatches = remoteDataSource.searchArtistAsync(input)
        artistMatches?.artist?.forEach {
            artists.add(it.mapToModel())
        }
        return artists
    }

    override suspend fun getTopAlbumsForArtist(artistId: String): ArrayList<TopAlbumModel> {
        val albums = ArrayList<TopAlbumModel>()
        val topAlbums = remoteDataSource.getTopAlbumsForArtistIdAsync(artistId)
        topAlbums.album?.forEach {
            albums.add(it.mapToModel())
        }
        return albums
    }

    override suspend fun getAlbumInfoForId(albumId: String): AlbumModel {
        val album = remoteDataSource.getAlbumInfoForId(albumId, Locale.getDefault().displayLanguage)
        return album.mapToModel()
    }

}