package ec.erickmedina.musicalisimo.ui.albums.detail

import androidx.lifecycle.LiveData
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.states.AlbumAction
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.musicalisimo.common.base.BaseContract

interface AlbumDetailContract {

    interface View : BaseContract.View {
        fun onAlbumSuccess(album: AlbumModel)
        fun onAlbumError(error:String?)
        fun onAlbumSaveSuccess()
        fun onAlbumSaveError(error: String?)
        fun onAlbumDeleteSuccess()
        fun onAlbumDeleteError(error: String?)
    }

    interface ViewModel : BaseContract.ViewModel {
        fun getAlbumObservable():LiveData<DataState<AlbumModel>>
        fun getAlbumActionObservable():LiveData<Pair<AlbumAction, AlbumModel?>>
        fun getAlbumInfoFor(albumId:String)
        fun saveAlbum(album:AlbumModel)
        fun deleteAlbum(album: AlbumModel)
    }
}