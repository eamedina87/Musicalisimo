package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.repository.Repository

class SaveOrDeleteAlbumUseCase(private val repository: Repository) : UseCase<Boolean, SaveOrDeleteAlbumUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): Boolean {
        if (params==null || params.album.isEmpty())
            return false
        when (params.delete) {
            true -> {
                //Delete Album
                val deleted = repository.deleteAlbum(params.album)
                return deleted == params.album
            }
            false -> {
                //Save Album
                val saved = repository.saveAlbum(params.album)
                return saved == params.album
            }
        }
    }

    data class Params(val album:AlbumModel, val delete:Boolean = false)
}