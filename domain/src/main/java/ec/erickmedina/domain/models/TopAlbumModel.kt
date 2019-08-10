package ec.erickmedina.domain.models

data class TopAlbumModel (val name: String, val playcount: Long, val id: String, val url: String,
                          val artist: ArtistModel, val image:ArrayList<ImageModel>)

fun TopAlbumModel.getImage(size: ImageSize = ImageSize.Large) : String {
    return image.single { it.size == size.size  }.url
}


