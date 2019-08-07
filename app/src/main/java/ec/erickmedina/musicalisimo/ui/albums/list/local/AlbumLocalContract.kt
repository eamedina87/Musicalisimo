package ec.erickmedina.musicalisimo.ui.albums.list.local

import androidx.lifecycle.LiveData
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.musicalisimo.common.base.BaseContract

interface AlbumLocalContract {

    interface View : BaseContract.View {
        fun onLocalAlbumsLoaded(albumList: ArrayList<AlbumModel>)
        fun onLocalAlbumsEmpty()
        fun onLocalAlbumsError(error:String?)
    }

    interface ViewModel : BaseContract.ViewModel {
        fun getLocalAlbumsObservable(): LiveData<DataState<LiveData<ArrayList<AlbumModel>>>>
        fun getLocalAlbums()
    }
}