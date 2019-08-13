package ec.erickmedina.data.remote.datasource.artist


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.remote.client.RemoteClient

class ArtistDataSourceFactory(private val remoteClient: RemoteClient, private val artist: String,
                              private val error:String) :
    DataSource.Factory<String, LastFmResponses.Artist>() {


    val dataSource = MutableLiveData<ArtistDataSource>()

    override fun create(): DataSource<String, LastFmResponses.Artist> {
        val source = ArtistDataSource(remoteClient, artist, error)
        dataSource.postValue(source)
        return source
    }
}