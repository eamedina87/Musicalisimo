package ec.erickmedina.data.remote

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.util.UtilsMock
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response

class RemoteClientTest {

    @Test
    fun `parses artist search result successfully`() {
        val mockedApi = mockk<LastFmApi> {
            every { searchArtistAsync(any(), any(), any(), any(), any(), any()) } answers {
                val json = UtilsMock.getSearchArtistResponseJson()
                val data = Gson().fromJson(json, LastFmResponses.SearchArtistResponse::class.java)
                val response = Response.success(data)
                CompletableDeferred(response)
            }
        }
        val mockedRemoteClient = mockk<RemoteClient> {
            every { getRemoteCaller() } answers { mockedApi }
        }
        runBlocking {
            val response = mockedRemoteClient.getRemoteCaller().searchArtistAsync(apiKey = "", artist = "").await()
            assert(response.isSuccessful)
            assert(response.body() != null)
            val bodyResponse = response.body()!!
            assertThat(bodyResponse.results).isNotNull()
            assertThat(bodyResponse.results.artistmatches).isNotNull()
            assertThat(bodyResponse.results.artistmatches.artist).isNotNull()
            val artistList = bodyResponse.results.artistmatches.artist
            assertThat(artistList).isNotEmpty()
            with(artistList[0]) {
                assertThat(name).isNotNull()
                assertThat(name).isNotEmpty()
                assertThat(mbid).isNotNull()
                assertThat(mbid).isNotEmpty()
                assertThat(url).isNotNull()
                assertThat(url).isNotEmpty()
                assertThat(listeners).isNotNull()
                assertThat(listeners).isAtLeast(0)
                assertThat(streamable).isNotNull()
                assertThat(streamable).isAtLeast(0)
                assertThat(image[0]).isNotNull()
                assertThat(image[0].text).isNotEmpty()
                assertThat(image[0].size).isNotEmpty()
            }
        }
    }

}