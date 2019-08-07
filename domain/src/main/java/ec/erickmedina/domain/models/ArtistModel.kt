package ec.erickmedina.domain.models

data class ArtistModel (
    val name: String,
    val listeners: Long,
    val id: String,
    val images: ArrayList<ImageModel>)