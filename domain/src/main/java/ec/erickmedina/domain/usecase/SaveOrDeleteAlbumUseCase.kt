package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.repository.Repository

class SaveOrDeleteAlbumUseCase(private val repository: Repository) : UseCase<AlbumModel?, SaveOrDeleteAlbumUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): AlbumModel? {
        if (params==null || params.album.isEmpty())
            return null
        return when (params.delete) {
            true -> {
                //Delete Album
                repository.deleteAlbum(params.album)
            }
            false -> {
                //Save Album
                val album = params.album
                album.isSaved = true
                repository.saveAlbum(album)
            }
        }
    }

    data class Params(val album:AlbumModel, val delete:Boolean = false)
}