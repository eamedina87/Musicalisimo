package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.DataState

class TopAlbumsUseCase(private val repository: Repository) : UseCase<DataState<ArrayList<TopAlbumModel>>, TopAlbumsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): DataState<ArrayList<TopAlbumModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    data class Params(val artistId:String)
}