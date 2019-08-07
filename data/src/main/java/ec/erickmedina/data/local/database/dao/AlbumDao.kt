package ec.erickmedina.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.local.database.entity.DatabaseEntities

@Dao
interface AlbumDao {

    @Insert(onConflict = REPLACE)
    fun insertAlbum(album: DatabaseEntities.AlbumEntity):Long

    @Delete
    fun deleteAlbum(album: DatabaseEntities.AlbumEntity):Int

    @Query("Select * from albumJson order by id")
    fun getAllAlbums():LiveData<List<DatabaseEntities.AlbumEntity>>

    @Query("Select * from albumJson order by artist asc")
    fun getAllAlbumsByArtist():LiveData<List<DatabaseEntities.AlbumEntity>>

    @Query("Select * from albumJson order by name asc")
    fun getAllAlbumsByName():LiveData<List<DatabaseEntities.AlbumEntity>>

    @Query("Select * from albumJson order by playcount asc")
    fun getAllAlbumsByPlaycount():LiveData<List<DatabaseEntities.AlbumEntity>>

}