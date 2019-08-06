package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.DataState
import java.lang.Exception

class SearchArtistUseCase(private val repository: Repository) : UseCase<DataState<ArrayList<ArtistModel>>, SearchArtistUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): DataState<ArrayList<ArtistModel>> {
        if (params==null || params.input.isEmpty()) {
            return DataState.Error("Artist name must be specified")
        }
        return try {
            val artistList = repository.searchArtistWithInput(params.input)
            DataState.Success(artistList)
        } catch (e:Exception) {
            DataState.Error(e.message ?: "An error ocurred getting the Artists")
        }
    }

    data class Params(val input:String)
}