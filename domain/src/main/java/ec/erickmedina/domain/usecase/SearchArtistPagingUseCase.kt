package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.Listing
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.DataState
import java.lang.Exception

class SearchArtistPagingUseCase(private val repository: Repository) : UseCase<Listing<ArtistModel>?, SearchArtistPagingUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): Listing<ArtistModel>? =
        if (params == null || params.input.isEmpty()) null
        else repository.searchArtist(params.input)

    data class Params(val input:String)
}