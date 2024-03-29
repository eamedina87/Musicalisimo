package ec.erickmedina.data.local.datasource

import androidx.lifecycle.LiveData
import ec.erickmedina.data.local.database.entity.DatabaseEntities
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.states.AlbumFilter

interface LocalDataSource {
    suspend fun getSavedAlbums(filter: AlbumFilter = AlbumFilter.Id) : LiveData<List<DatabaseEntities.AlbumEntity>>
    suspend fun getAlbumInfoForId(albumId: String): DatabaseEntities.AlbumEntity?
    suspend fun saveAlbum(album: DatabaseEntities.AlbumEntity) : Long
    suspend fun deleteAlbum(album:DatabaseEntities.AlbumEntity) : Boolean
}