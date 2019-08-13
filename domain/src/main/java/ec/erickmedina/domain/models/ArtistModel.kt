package ec.erickmedina.domain.models

data class ArtistModel (
    val name: String,
    val listeners: Long,
    val id: String,
    val images: ArrayList<ImageModel>)

fun ArtistModel.getImage(size: ImageSize = ImageSize.Large) : String {
    return images.single { it.size == size.size  }.url
}