package ec.erickmedina.domain.usecase

import androidx.lifecycle.LiveData
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.states.DataState

class LocalAlbumsUseCase : UseCase<DataState<LiveData<ArrayList<AlbumModel>>>, LocalAlbumsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): DataState<LiveData<ArrayList<AlbumModel>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class Params
}