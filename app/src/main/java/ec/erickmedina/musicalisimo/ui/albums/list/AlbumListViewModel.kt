package ec.erickmedina.musicalisimo.ui.albums.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.domain.usecase.LocalAlbumsUseCase
import ec.erickmedina.domain.usecase.TopAlbumsUseCase
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import ec.erickmedina.musicalisimo.ui.albums.list.local.AlbumLocalContract
import ec.erickmedina.musicalisimo.ui.albums.list.top.AlbumTopContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumListViewModel(private val topAlbumsUseCase: TopAlbumsUseCase,
                         private val localAlbumsUseCase: LocalAlbumsUseCase) :
    BaseViewModel(), AlbumTopContract.ViewModel, AlbumLocalContract.ViewModel {


    private val topAlbums : MutableLiveData<DataState<ArrayList<TopAlbumModel>>> =
        MutableLiveData()

    private val localAlbums : MutableLiveData<DataState<LiveData<ArrayList<AlbumModel>>>> =
        MutableLiveData()

    override fun getTopAlbumsObservable(): LiveData<DataState<ArrayList<TopAlbumModel>>> =
        topAlbums

    override fun getLocalAlbumsObservable(): LiveData<DataState<LiveData<ArrayList<AlbumModel>>>> =
        localAlbums

    override fun getTopAlbumsForArtist(artist: String) {
        launch {
            val albums = withContext(Dispatchers.IO) {
                topAlbumsUseCase.execute(TopAlbumsUseCase.Params(artist))
            }
            topAlbums.postValue(albums)
        }
    }

    override fun getLocalAlbums() {
        launch {
            val albums = withContext(Dispatchers.IO) {
                localAlbumsUseCase.execute()
            }
            localAlbums.postValue(albums)
        }
    }

}