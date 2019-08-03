package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.AlbumModel

class SaveAlbumUseCase : UseCase<Boolean, SaveAlbumUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    data class Params(val album:AlbumModel, val delete:Boolean = false)
}