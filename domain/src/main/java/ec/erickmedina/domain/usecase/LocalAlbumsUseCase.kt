package ec.erickmedina.domain.usecase

import androidx.lifecycle.LiveData
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.AlbumFilter
import ec.erickmedina.domain.states.DataState
import java.lang.Exception

class LocalAlbumsUseCase(private val repository: Repository) : UseCase<DataState<LiveData<ArrayList<AlbumModel>>>, LocalAlbumsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): DataState<LiveData<ArrayList<AlbumModel>>> {
        return try {
            val filter = params?.albumFilter ?: AlbumFilter.Id
            val messages = repository.getLocalAlbums(filter)
            return DataState.Success(messages)
        } catch (e:Exception) {
            DataState.Error(e.message ?: "An error occurred btaining the Saved Albums")
        }
    }

    class Params(val albumFilter: AlbumFilter)
}