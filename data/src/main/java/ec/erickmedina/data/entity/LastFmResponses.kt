package ec.erickmedina.data.entity

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import ec.erickmedina.domain.models.ImageSize


object LastFmResponses {
    //Pagination
    data class Pagination(val startPage: String, val searchTerms: String)
    //Default Classes
    data class Artist(val name:String, val listeners:Long, val mbid:String,
                      val url:String, val streamable:Int, val image:Array<Image>)
    data class Image(@SerializedName("#text") val text:String, val size:String)
    //Search Artist
    data class SearchArtistResponse(val results: SearchArtistResults)
    data class SearchArtistResults(val artistmatches: SearchArtistMatches,
                                   @SerializedName("opensearch:Query") val pagination:Pagination,
                                   @SerializedName("opensearch:startIndex") val startIndex:String,
                                   @SerializedName("opensearch:itemsPerPage") val pageItems: String,
                                   @SerializedName("opensearch:totalResults") val totalResults: String)
    data class SearchArtistMatches(val artist: Array<Artist>)
    //Top ALbums
    data class TopAlbumResponse(val topalbums: TopAlbums)
    data class TopAlbums(val album: Array<TopAlbum>)
    data class TopAlbum(val name:String, val playcount:Long, val mbid: String, val url:String,
                        val artist: Artist, val image: Array<Image>)
    //Album Info
    data class AlbumInfoResponse(val album: Album)
    data class Album(val name: String, val artist: String, val mbid: String, val url: String,
                     val image: Array<Image>, val listeners: Long, val playcount: Long,
                     val tracks: Tracks, val tags:Tags, val wiki:Wiki, var isSaved:Boolean = false)
    data class Tracks(val track:Array<Track>)
    data class Track(val name: String, val url: String, val duration: Int,
                     @SerializedName("@attr") val attr: Map<String, String>,
                     val streamable: Map<String, String>,
                     val artist: Artist)
    data class Tags(val tag:Array<Tag>)
    data class Tag(val name: String, val url:String)
    data class Wiki(val published: String, val summary: String, val content:String)

}

fun LastFmResponses.Artist.getImage(size: ImageSize = ImageSize.Large) : String {
    return image.single { it.size == size.size }.text ?: ""
}