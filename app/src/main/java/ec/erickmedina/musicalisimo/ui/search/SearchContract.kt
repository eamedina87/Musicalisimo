package ec.erickmedina.musicalisimo.ui.search

import androidx.lifecycle.LiveData
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.musicalisimo.common.base.BaseContract

interface SearchContract {

    interface View : BaseContract.View {
        fun onArtistSearchSuccess(list: ArrayList<ArtistModel>)
        fun onArtistSearchEmpty()
        fun onArtistSearchError(error: String)
    }

    interface ViewModel : BaseContract.ViewModel {
        fun getArtistListObservable():LiveData<DataState<ArrayList<ArtistModel>>>
        fun getArtistForInput(input:String)
    }
}