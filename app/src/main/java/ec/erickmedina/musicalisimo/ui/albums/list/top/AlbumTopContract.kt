package ec.erickmedina.musicalisimo.ui.albums.list.top

import androidx.lifecycle.LiveData
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.musicalisimo.common.base.BaseContract

interface AlbumTopContract {

    interface View : BaseContract.View {
        fun onTopAlbumsLoaded(albumList: ArrayList<TopAlbumModel>)
        fun onTopAlbumsEmpty()
        fun onTopAlbumsError(error: String?)
    }

    interface ViewModel : BaseContract.ViewModel {
        fun getTopAlbumsObservable(): LiveData<DataState<ArrayList<TopAlbumModel>>>
        fun getTopAlbumsForArtist(artist:String)
    }
}