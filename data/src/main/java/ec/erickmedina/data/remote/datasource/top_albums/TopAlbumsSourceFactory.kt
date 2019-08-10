package ec.erickmedina.data.remote.datasource.top_albums


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.remote.client.RemoteClient

class TopAlbumsSourceFactory(private val remoteClient: RemoteClient, private val artist: String,
                             private val error:String) :
    DataSource.Factory<String, LastFmResponses.TopAlbum>() {


    val dataSource = MutableLiveData<TopAlbumsDataSource>()

    override fun create(): DataSource<String, LastFmResponses.TopAlbum> {
        val source = TopAlbumsDataSource(remoteClient, artist, error)
        dataSource.postValue(source)
        return source
    }
}