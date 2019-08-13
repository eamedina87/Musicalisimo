package ec.erickmedina.data.remote.datasource.artist

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

class ArtistDataSource(remoteClient: RemoteClient, private val artist: String,
                       private val error:String) :
    PageKeyedDataSource<String, LastFmResponses.Artist>() {

    private val caller = remoteClient.getRemoteCaller()

    private var dataState : MutableLiveData<NetworkState> = MutableLiveData()

    fun getDataState() : LiveData<NetworkState> = dataState

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, LastFmResponses.Artist>) {
        try {
            dataState.postValue(NetworkState.Loading)
            val response = caller.searchArtists(artist = artist, apiKey = BuildConfig.API_KEY).execute().handleErrors(error)
            val list = response.results.artistmatches.artist.map { it } ?: emptyList()
            callback.onResult(list, null, response.results.pagination.startPage.nextIndex() ?: "1")
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
        callback: LoadCallback<String, LastFmResponses.Artist>) {
        try {
            dataState.postValue(NetworkState.Loading)
            val response = caller.searchArtists(artist = artist, apiKey = BuildConfig.API_KEY,
                page = params.key).execute().handleErrors(error)
            val list = response.results.artistmatches.artist.map { it } ?: emptyList()
            callback.onResult(list, response.results.pagination.startPage.nextIndex() ?: "1")
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
        callback: LoadCallback<String, LastFmResponses.Artist>
    ) {
        //not needed
    }
}