package ec.erickmedina.domain.models

data class ImageModel(val url:String, val size:String)

sealed class ImageSize {
    abstract var size: String
    object Small: ImageSize() {
        override var size = "small"
    }
    object Medium: ImageSize() {
        override var size = "medium"
    }
    object Large: ImageSize() {
        override var size = "large"
    }
    object ExtraLarge: ImageSize() {
        override var size = "extralarge"
    }
    object Mega: ImageSize() {
        override var size = "mega"
    }
}