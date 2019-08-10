package ec.erickmedina.musicalisimo.ui.search

import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.musicalisimo.common.base.BaseViewModel

class SearchPagingViewModel(private val repository: Repository) : BaseViewModel(), SearchContract.ViewModel {

    override fun getArtistForInput(input: String) =
            repository.searchArtist(input)


}