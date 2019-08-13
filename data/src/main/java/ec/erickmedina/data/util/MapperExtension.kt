package ec.erickmedina.data.util

import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.local.database.entity.DatabaseEntities
import ec.erickmedina.domain.models.*

fun DatabaseEntities.AlbumEntity.mapToModel():AlbumModel {
    val albumModel = album.mapToModel()
    albumModel.localId = id
    return albumModel
}

fun LastFmResponses.Album.mapToModel() : AlbumModel {
    val images = arrayListOf<ImageModel>()
    image?.forEach {
        images.add(it.mapToModel())
    }
    val trackList = arrayListOf<TrackModel>()
    tracks?.track?.forEach {
        trackList.add(it.mapToModel())
    }
    val tagList = arrayListOf<TagModel>()
    tags?.tag?.forEach {
        tagList.add(it.mapToModel())
    }
    return AlbumModel(name, artist, 0, mbid ?: "", images, listeners,
        playcount, trackList, tagList, wiki?.published ?: "", wiki?.summary?: "",
        wiki?.content ?: "", isSaved)
}

fun LastFmResponses.Image.mapToModel() : ImageModel =
    ImageModel(text ?: "", size ?: "")

fun LastFmResponses.Track.mapToModel() : TrackModel =
    TrackModel(name ?: "", duration ?: 0, attr["rank"]?.toInt() ?: 0)

fun LastFmResponses.Tag.mapToModel() : TagModel =
    TagModel(name ?: "")

fun LastFmResponses.Artist.mapToModel() : ArtistModel {
    val images = arrayListOf<ImageModel>()
    image?.forEach {
        images.add(it.mapToModel())
    }
    return ArtistModel(name ?: "", listeners ?: 0, mbid ?: "", images)
}

fun LastFmResponses.TopAlbum.mapToModel() : TopAlbumModel {
    val images = arrayListOf<ImageModel>()
    image?.forEach {
        images.add(it.mapToModel())
    }
    return TopAlbumModel(name ?: "", playcount ?: 0, mbid ?: "", url ?: "",
        artist.mapToModel(), images)
}

fun AlbumModel.mapToDBEntity() : DatabaseEntities.AlbumEntity {
    return DatabaseEntities.AlbumEntity(localId, artist, name, playcount, remoteId, mapToRemoteEntity())
}

fun AlbumModel.mapToRemoteEntity() : LastFmResponses.Album {
    val image = images.map { it.mapToEntity() }.toTypedArray()
    val trackEntity = tracks.map { it.mapToEntity() }.toTypedArray()
    val tracksEntity = LastFmResponses.Tracks(trackEntity)
    val tagEntity = tags.map { it.mapToEntity() }.toTypedArray()
    val tagsEntity = LastFmResponses.Tags(tagEntity)
    return LastFmResponses.Album(name, artist, remoteId, "", image, listeners, playcount,
        tracksEntity, tagsEntity, LastFmResponses.Wiki(publication, summary, description), isSaved)
}

fun ImageModel.mapToEntity() : LastFmResponses.Image =
    LastFmResponses.Image(url, size)

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



