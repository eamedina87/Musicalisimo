package ec.erickmedina.domain.models

data class AlbumModel (
    val name:String,
    val artist:String,
    var localId: Long,
    val remoteId: String,
    val images: ArrayList<ImageModel>,
    val listeners: Long,
    val playcount: Long,
    val tracks: ArrayList<TrackModel>,
    val tags: ArrayList<TagModel>,
    val publication: String,
    val summary: String,
    val description: String,
    var isSaved: Boolean
    ) {
    fun isEmpty(): Boolean {
        return name.isEmpty() || artist.isEmpty() || tracks.isEmpty()
    }
}

fun AlbumModel.getImage(size: ImageSize = ImageSize.Large) : String {
    return if (images.isEmpty()) "" else
        images.single { it.size == size.size  }.url
}