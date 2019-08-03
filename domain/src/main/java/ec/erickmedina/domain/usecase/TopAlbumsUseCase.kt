package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.TopAlbumModel

class TopAlbumsUseCase : UseCase<ArrayList<TopAlbumModel>, TopAlbumsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): ArrayList<TopAlbumModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    data class Params(val artistId:String)
}