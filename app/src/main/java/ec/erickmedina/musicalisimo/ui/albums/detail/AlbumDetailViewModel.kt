package ec.erickmedina.musicalisimo.ui.albums.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.states.AlbumAction
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.domain.usecase.AlbumInfoUseCase
import ec.erickmedina.domain.usecase.SaveOrDeleteAlbumUseCase
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumDetailViewModel(private val albumInfoUseCase: AlbumInfoUseCase,
                           private val albumSaveOrDeleteUseCase: SaveOrDeleteAlbumUseCase) :
    BaseViewModel(), AlbumDetailContract.ViewModel {

    private val album : MutableLiveData<DataState<AlbumModel>> =
        MutableLiveData()
    private val albumAction : MutableLiveData<Pair<AlbumAction, AlbumModel?>> =
        MutableLiveData()

    override fun getAlbumObservable(): LiveData<DataState<AlbumModel>> =
        album

    override fun getAlbumActionObservable(): LiveData<Pair<AlbumAction, AlbumModel?>> =
        albumAction

    override fun getAlbumInfoFor(albumId: String) {
        launch {
            val albumInfo = withContext(Dispatchers.IO) {
                albumInfoUseCase.execute(AlbumInfoUseCase.Params(albumId))
            }
            album.postValue(albumInfo)
        }
    }

    override fun saveAlbum(album: AlbumModel) {
        launch {
            val saved = withContext(Dispatchers.IO) {
                albumSaveOrDeleteUseCase.execute(SaveOrDeleteAlbumUseCase.Params(album))
            }
            albumAction.postValue(Pair(AlbumAction.Save, saved))
        }
    }

    override fun deleteAlbum(album: AlbumModel) {
        launch {
            val saved = withContext(Dispatchers.IO) {
                albumSaveOrDeleteUseCase.execute(SaveOrDeleteAlbumUseCase.Params(album, true))
            }
            albumAction.postValue(Pair(AlbumAction.Delete, saved))
        }
    }


}