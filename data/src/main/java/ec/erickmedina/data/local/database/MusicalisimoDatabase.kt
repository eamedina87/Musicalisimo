package ec.erickmedina.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ec.erickmedina.data.local.database.converter.LastFmConverters
import ec.erickmedina.data.local.database.dao.AlbumDao
import ec.erickmedina.data.local.database.entity.DatabaseEntities

@Database(entities = [DatabaseEntities.AlbumEntity::class],
    version = 1, exportSchema = false)
@TypeConverters(LastFmConverters::class)
abstract class MusicalisimoDatabase : RoomDatabase () {
    abstract fun albumDao() : AlbumDao
}