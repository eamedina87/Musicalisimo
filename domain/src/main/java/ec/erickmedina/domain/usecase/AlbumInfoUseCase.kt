package ec.erickmedina.domain.usecase

import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.DataState
import java.lang.Exception

class AlbumInfoUseCase(private val repository: Repository) : UseCase<DataState<AlbumModel>, AlbumInfoUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): DataState<AlbumModel> {
        if (params==null || params.albumId.isEmpty())
            return DataState.Error("Album name or id must be specified")
        return try {
            val album = repository.getAlbumInfoForId(params.albumId)
            DataState.Success(album)
        } catch (e:Exception) {
            DataState.Error(e.message ?: "An error occurred getting the Album")
        }
    }

    data class Params(val albumId: String)
}