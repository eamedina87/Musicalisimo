package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.states.DataState

class SearchArtistUseCase : UseCase<DataState<ArrayList<ArtistModel>>, SearchArtistUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): DataState<ArrayList<ArtistModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    data class Params(val input:String)
}