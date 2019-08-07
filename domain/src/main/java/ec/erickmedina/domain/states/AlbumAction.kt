package ec.erickmedina.domain.states

sealed class AlbumAction {
    object Delete : AlbumAction()
    object Save : AlbumAction()
}