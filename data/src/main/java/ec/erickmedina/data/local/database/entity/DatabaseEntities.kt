package ec.erickmedina.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ec.erickmedina.data.entity.LastFmResponses

object DatabaseEntities {

    @Entity(tableName = "albumJson")
    data class AlbumEntity(@PrimaryKey(autoGenerate = true) var id: Long, val artist: String,
                           val name: String, val playcount: Long, val album: LastFmResponses.Album) {

        constructor(entity: LastFmResponses.Album) : this(0, entity.artist, entity.name, entity.playcount, entity)

    }

}
