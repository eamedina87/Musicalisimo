package ec.erickmedina.data.remote.datasource.top_albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import ec.erickmedina.data.BuildConfig
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.remote.client.RemoteClient
import ec.erickmedina.data.util.handleErrors
import ec.erickmedina.data.util.nextIndex
import ec.erickmedina.domain.exceptions.NoConnectivityException
import ec.erickmedina.domain.states.NetworkState
import java.io.IOException
import java.lang.Exception

class TopAlbumsDataSource(remoteClient: RemoteClient, private val artist: String,
                          private val error:String) :
    PageKeyedDataSource<String, LastFmResponses.TopAlbum>() {

    private val caller = remoteClient.getRemoteCaller()

    private var dataState : MutableLiveData<NetworkState> = MutableLiveData()

    fun getDataState() : LiveData<NetworkState> = dataState

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, LastFmResponses.TopAlbum>) {
        try {
            dataState.postValue(NetworkState.Loading)
            val response = caller.getTopAlbumsForArtist(artist = artist, apiKey = BuildConfig.API_KEY).execute().handleErrors(error)
            val list = response.topalbums.album.map { it } ?: emptyList()
            callback.onResult(list, null, response.topalbums.attr.page.nextIndex() ?: "1")
            dataState.postValue(NetworkState.Success)
        } catch (e: NoConnectivityException) {
            dataState.postValue(NetworkState.Error(e.message))
        } catch (e: IOException) {
            dataState.postValue(NetworkState.Error(e.message ?: "IO error"))
        } catch (e: Exception) {
            dataState.postValue(NetworkState.Error(e.message ?: "Unknown error"))
        }
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, LastFmResponses.TopAlbum>) {
        try {
            dataState.postValue(NetworkState.Loading)
            val response = caller.getTopAlbumsForArtist(artist = artist, apiKey = BuildConfig.API_KEY,
                page = params.key).execute().handleErrors(error)
            val list = response.topalbums.album.map { it } ?: emptyList()
            callback.onResult(list, response.topalbums.attr.page.nextIndex() ?: "1")
            dataState.postValue(NetworkState.Success)
        } catch (e: NoConnectivityException) {
            dataState.postValue(NetworkState.Error(e.message))
        } catch (e: IOException) {
            dataState.postValue(NetworkState.Error(e.message ?: "IO error"))
        } catch (e: Exception) {
            dataState.postValue(NetworkState.Error(e.message ?: "Unknown error"))
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, LastFmResponses.TopAlbum>
    ) {
        //not needed
    }
}