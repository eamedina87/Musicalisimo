package ec.erickmedina.musicalisimo.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.Listing
import ec.erickmedina.domain.usecase.SearchArtistPagingUseCase
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchPagingViewModel(private val useCase: SearchArtistPagingUseCase) : BaseViewModel(), SearchContract.ViewModel {

    private val artist : MutableLiveData<Listing<ArtistModel>?> = MutableLiveData()

    override fun getArtist(): LiveData<Listing<ArtistModel>?> = artist

    override fun getArtistForInput(input: String) {
        launch {
            val temp = withContext(Dispatchers.IO) {
                useCase.execute(SearchArtistPagingUseCase.Params(input))
            }
            artist.postValue(temp)
        }
    }


}