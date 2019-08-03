package ec.erickmedina.data.remote

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.remote.client.LastFmApi
import ec.erickmedina.data.remote.client.RemoteClient
import ec.erickmedina.data.utils.UtilsMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response

class RemoteClientTest {

    @Test
    fun `get artist search result successfully`() {
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
            verify { mockedApi.searchArtistAsync(any(), any(), any(), any(), any()) }
            assert(response.isSuccessful)
            assert(response.body() != null)
            val bodyResponse = response.body()!!
            assertThat(bodyResponse.results).isNotNull()
            assertThat(bodyResponse.results.artistmatches).isNotNull()
            assertThat(bodyResponse.results.artistmatches.artist).isNotNull()
            val artistList = bodyResponse.results.artistmatches.artist
            assertThat(artistList).isNotEmpty()
            with(artistList[0]) {
                assertThat(name).isNotEmpty()
                assertThat(mbid).isNotEmpty()
                assertThat(url).isNotEmpty()
                assertThat(listeners).isAtLeast(0)
                assertThat(streamable).isAtLeast(0)
                assertThat(image[0]).isNotNull()
                assertThat(image[0].text).isNotEmpty()
                assertThat(image[0].size).isNotEmpty()
            }
        }
    }

    @Test
    fun `get top albums result successfully`() {
        val mockedApi = mockk<LastFmApi> {
            every { getTopAlbumsForArtistIdAsync(any(), any(), any(), any(), any(), any()) } answers {
                val json = UtilsMock.getTopAlbumsResponseJson()
                val data = Gson().fromJson(json, LastFmResponses.TopAlbumResponse::class.java)
                val response = Response.success(data)
                CompletableDeferred(response)
            }
        }
        val mockedRemoteClient = mockk<RemoteClient> {
            every { getRemoteCaller() } answers { mockedApi }
        }
        runBlocking {
            val response = mockedRemoteClient.getRemoteCaller().getTopAlbumsForArtistIdAsync(apiKey = "", artistId = "").await()
            verify { mockedApi.getTopAlbumsForArtistIdAsync(any(), any(), any(), any(), any()) }
            assert(response.isSuccessful)
            assert(response.body() != null)
            val bodyResponse = response.body()!!
            assertThat(bodyResponse.topalbums).isNotNull()
            assertThat(bodyResponse.topalbums.album).isNotNull()
            val albumList = bodyResponse.topalbums.album
            assertThat(albumList).isNotEmpty()
            with(albumList[0]) {
                assertThat(name).isNotEmpty()
                assertThat(playcount).isAtLeast(0)
                assertThat(mbid).isNotEmpty()
                assertThat(url).isNotEmpty()
                assertThat(artist).isNotNull()
                assertThat(artist.name).isNotEmpty()
                assertThat(artist.mbid).isNotEmpty()
                assertThat(artist.url).isNotEmpty()
                assertThat(image[0]).isNotNull()
                assertThat(image[0].text).isNotEmpty()
                assertThat(image[0].size).isNotEmpty()
            }
        }
    }

    @Test
    fun `get album info result successfully`() {
        val mockedApi = mockk<LastFmApi> {
            every { getAlbumInfoForIdAsync(any(), any(), any(), any(), any()) } answers {
                val json = UtilsMock.getAlbumInfoResponseJson()
                val data = Gson().fromJson(json, LastFmResponses.AlbumInfoResponse::class.java)
                val response = Response.success(data)
                CompletableDeferred(response)
            }
        }
        val mockedRemoteClient = mockk<RemoteClient> {
            every { getRemoteCaller() } answers { mockedApi }
        }
        runBlocking {
            val response = mockedRemoteClient.getRemoteCaller().getAlbumInfoForIdAsync(apiKey = "", albumId = "", lang = "").await()
            verify { mockedApi.getAlbumInfoForIdAsync(any(), any(), any(), any(), any()) }
            assert(response.isSuccessful)
            assert(response.body() != null)
            val bodyResponse = response.body()!!
            assertThat(bodyResponse.album).isNotNull()
            with(bodyResponse.album) {
                assertThat(name).isNotEmpty()
                assertThat(artist).isNotEmpty()
                assertThat(mbid).isNotEmpty()
                assertThat(url).isNotEmpty()
                assertThat(listeners).isAtLeast(0)
                assertThat(playcount).isAtLeast(0)
                assertThat(image[0]).isNotNull()
                assertThat(image[0].text).isNotEmpty()
                assertThat(image[0].size).isNotEmpty()
                assertThat(tracks).isNotNull()
                assertThat(tracks.track).isNotEmpty()
                assertThat(tracks.track[0]).isNotNull()
                with(tracks.track[0]) {
                    assertThat(name).isNotEmpty()
                    assertThat(artist).isNotNull()
                    assertThat(mbid).isNotEmpty()
                    assertThat(url).isNotEmpty()
                    assertThat(attr).isNotEmpty()
                }
                assertThat(tags).isNotNull()
                assertThat(tags.tag).isNotNull()
                assertThat(tags.tag[0]).isNotNull()
                with(tags.tag[0]) {
                    assertThat(name).isNotEmpty()
                    assertThat(url).isNotEmpty()
                }
                assertThat(wiki).isNotNull()
                with(wiki) {
                    assertThat(content).isNotEmpty()
                    assertThat(published).isNotEmpty()
                    assertThat(summary).isNotEmpty()
                }
            }

        }
    }

}