package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.DataState
import java.lang.Exception

class TopAlbumsUseCase(private val repository: Repository) : UseCase<DataState<ArrayList<TopAlbumModel>>, TopAlbumsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): DataState<ArrayList<TopAlbumModel>> {
        if (params==null || params.artistId.isEmpty()) {
            return DataState.Error("Artist name or id must be specified")
        }
        return try {
            val topAlbums = repository.getTopAlbumsForArtist(params.artistId)
            DataState.Success(topAlbums)
        } catch (e:Exception) {
            DataState.Error(e.message ?: "An error occurred getting the Top ALbums")
        }
    }

    data class Params(val artistId:String)
}