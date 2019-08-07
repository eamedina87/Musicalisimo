package ec.erickmedina.domain.states

sealed class AlbumFilter {
    object Id : AlbumFilter()
    object Artist : AlbumFilter()
    object Name : AlbumFilter()
    object Playcount : AlbumFilter()

}