package ec.erickmedina.data.local.database.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import ec.erickmedina.data.entity.LastFmResponses

class LastFmConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromAlbum(value: LastFmResponses.Album): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Album::class.java)
            return adapter.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toAlbum(value: String): LastFmResponses.Album {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Album::class.java)
            return adapter.fromJson(value)!!
        }

        @TypeConverter
        @JvmStatic
        fun fromImage(value: LastFmResponses.Image): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Image::class.java)
            return adapter.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toImage(value: String): LastFmResponses.Image {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Image::class.java)
            return adapter.fromJson(value)!!
        }

        @TypeConverter
        @JvmStatic
        fun fromTracks(value: LastFmResponses.Tracks): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Tracks::class.java)
            return adapter.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toTracks(value: String): LastFmResponses.Tracks {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Tracks::class.java)
            return adapter.fromJson(value)!!
        }

        @TypeConverter
        @JvmStatic
        fun fromTrack(value: LastFmResponses.Track): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Track::class.java)
            return adapter.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toTrack(value: String): LastFmResponses.Track {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Track::class.java)
            return adapter.fromJson(value)!!
        }

        @TypeConverter
        @JvmStatic
        fun fromTags(value: LastFmResponses.Tags): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Tags::class.java)
            return adapter.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toTags(value: String): LastFmResponses.Tags {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Tags::class.java)
            return adapter.fromJson(value)!!
        }

        @TypeConverter
        @JvmStatic
        fun fromTag(value: LastFmResponses.Tag): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Tag::class.java)
            return adapter.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toTag(value: String): LastFmResponses.Tag {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Tag::class.java)
            return adapter.fromJson(value)!!
        }

        @TypeConverter
        @JvmStatic
        fun fromWiki(value: LastFmResponses.Wiki): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Wiki::class.java)
            return adapter.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toWiki(value: String): LastFmResponses.Wiki {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(LastFmResponses.Wiki::class.java)
            return adapter.fromJson(value)!!
        }

    }
}