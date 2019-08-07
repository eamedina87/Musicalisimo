package ec.erickmedina.musicalisimo.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.domain.usecase.SearchArtistUseCase
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val searchArtistUseCase: SearchArtistUseCase) : BaseViewModel(), SearchContract.ViewModel {

    private val artistList : MutableLiveData<DataState<ArrayList<ArtistModel>>> =
        MutableLiveData()

    override fun getArtistListObservable(): LiveData<DataState<ArrayList<ArtistModel>>> =
        artistList

    override fun getArtistForInput(input: String) {
        launch {
            val artists = withContext(Dispatchers.IO) {
                searchArtistUseCase.execute(SearchArtistUseCase.Params(input))
            }
            artistList.postValue(artists)
        }
    }


}