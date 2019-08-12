package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.Listing
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.DataState
import java.lang.Exception

class TopAlbumsPagingUseCase(private val repository: Repository) : UseCase<Listing<TopAlbumModel>?, TopAlbumsPagingUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): Listing<TopAlbumModel>? =
        if (params==null || params.artistId.isEmpty()) null
        else repository.getTopAlbumsFor(params.artistId)

    data class Params(val artistId:String)
}