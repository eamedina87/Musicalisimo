package ec.erickmedina.data.local.datasource

import androidx.lifecycle.LiveData
import ec.erickmedina.data.local.database.MusicalisimoDatabase
import ec.erickmedina.data.local.database.entity.DatabaseEntities
import ec.erickmedina.data.util.mapToModel
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.states.AlbumFilter

class LocalDataSourceImpl(private val database:MusicalisimoDatabase) : LocalDataSource {

    private val mDao = database.albumDao()

    override suspend fun getSavedAlbums(filter: AlbumFilter): LiveData<List<DatabaseEntities.AlbumEntity>> {
        return when (filter) {
            is AlbumFilter.Id -> mDao.getAllAlbums()
            is AlbumFilter.Artist -> mDao.getAllAlbumsByArtist()
            is AlbumFilter.Name -> mDao.getAllAlbumsByName()
            is AlbumFilter.Playcount -> mDao.getAllAlbumsByPlaycount()
        }
    }

    override suspend fun getAlbumInfoForId(albumId: String): DatabaseEntities.AlbumEntity? =
        mDao.getAlbumWithId(albumId)


    override suspend fun saveAlbum(album: DatabaseEntities.AlbumEntity): Long {
        return mDao.insertAlbum(album)
    }

    override suspend fun deleteAlbum(album: DatabaseEntities.AlbumEntity): Boolean {
        val deleted = mDao.deleteAlbum(album)
        return deleted > 0
    }

}