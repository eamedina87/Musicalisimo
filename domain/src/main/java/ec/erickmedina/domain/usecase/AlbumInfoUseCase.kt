package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.states.DataState

class AlbumInfoUseCase : UseCase<DataState<AlbumModel>, AlbumInfoUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): DataState<AlbumModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    data class Params(val albumId: Long)
}