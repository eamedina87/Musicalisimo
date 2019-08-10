package ec.erickmedina.musicalisimo.ui.search

import androidx.paging.PagedList
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.Listing
import ec.erickmedina.musicalisimo.common.base.BaseContract

interface SearchContract {

    interface View : BaseContract.View {
        fun onArtistSearchSuccess(list: PagedList<ArtistModel>)
        fun onArtistSearchEmpty()
        fun onArtistSearchError(error: String?)
    }

    interface ViewModel : BaseContract.ViewModel {
        fun getArtistForInput(input:String):Listing<ArtistModel>
    }
}