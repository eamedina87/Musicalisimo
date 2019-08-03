package ec.erickmedina.data.util

import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.local.database.entity.DatabaseEntities
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.ImageModel
import ec.erickmedina.domain.models.TagModel
import ec.erickmedina.domain.models.TrackModel

fun DatabaseEntities.AlbumEntity.mapToModel():AlbumModel {
    val images = arrayListOf<ImageModel>()
    album.image?.forEach {
        images.add(it.mapToModel())
    }
    val tracks = arrayListOf<TrackModel>()
    album.tracks?.track?.forEach {
        tracks.add(it.mapToModel())
    }
    val tags = arrayListOf<TagModel>()
    album.tags?.tag?.forEach {
        tags.add(it.mapToModel())
    }
    return AlbumModel(album.name ?: "", album.artist ?: "", id, album.mbid ?: "", images,album.listeners ?: 0,
        album.playcount ?: 0, tracks, tags, album.wiki?.published ?: "",
        album.wiki?.summary ?: "", album.wiki?.content ?: "")
}


fun LastFmResponses.Image.mapToModel() : ImageModel =
    ImageModel(text ?: "", size ?: "")

fun LastFmResponses.Track.mapToModel() : TrackModel =
    TrackModel(name ?: "", duration ?: 0, attr["rank"]?.toInt() ?: 0)

fun LastFmResponses.Tag.mapToModel() : TagModel =
    TagModel(name ?: "")

fun AlbumModel.mapToDBEntity() : DatabaseEntities.AlbumEntity {
    return DatabaseEntities.AlbumEntity(localId, artist, name, playcount, mapToRemoteEntity())
}

fun AlbumModel.mapToRemoteEntity() : LastFmResponses.Album {
    val image = arrayOf<LastFmResponses.Image>()
    val trackEntity = arrayOf<LastFmResponses.Track>()
    val tracksEntity = LastFmResponses.Tracks(trackEntity)
    this.tracks?.forEach {
        trackEntity.plus(it.mapToEntity())
    }
    val tagEntity = arrayOf<LastFmResponses.Tag>()
    val tagsEntity = LastFmResponses.Tags(tagEntity)
    this.tags?.forEach {
        tagEntity.plus(it.mapToEntity())
    }
    return LastFmResponses.Album(name, artist, remoteId, "", image, listeners, playcount,
        tracksEntity, tagsEntity, LastFmResponses.Wiki(publication, summary, description))
}

fun TagModel.mapToEntity() : LastFmResponses.Tag =
    LastFmResponses.Tag(name, "")

fun TrackModel.mapToEntity() : LastFmResponses.Track =
    LastFmResponses.Track(name, "", duration, order.toTrackAttribute(), HashMap(),
        LastFmResponses.Artist("", 0, "", "", 0, arrayOf()))

fun Int.toTrackAttribute() : Map<String, String> {
    val map = HashMap<String, String>()
    map["rank"] = this.toString()
    return map
}



