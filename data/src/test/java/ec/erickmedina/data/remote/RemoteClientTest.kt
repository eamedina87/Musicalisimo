package ec.erickmedina.data.remote

import com.google.common.truth.Truth.assertThat
import ec.erickmedina.data.remote.client.LastFmApi
import ec.erickmedina.data.remote.client.RemoteClient
import ec.erickmedina.data.utils.UtilsAssertion
import ec.erickmedina.data.utils.UtilsMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemoteClientTest {

    @Test
    fun `get artist search result successfully`() {
        val mockedApi = mockk<LastFmApi> {
            every { searchArtistAsync(any(), any(), any(), any(), any(), any()) } answers {
                UtilsMock.getSearchArtistDeferredResponse()
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
            assertThat(bodyResponse.results.pagination).isNotNull()
            assertThat(bodyResponse.results.artistmatches).isNotNull()
            assertThat(bodyResponse.results.artistmatches.artist).isNotNull()
            val artistList = bodyResponse.results.artistmatches.artist
            assertThat(artistList).isNotEmpty()
            artistList.forEach {
                UtilsAssertion.assertArtistEntity(it)
            }
        }
    }

    @Test
    fun `get top albums result successfully`() {
        val mockedApi = mockk<LastFmApi> {
            every { getTopAlbumsForArtistIdAsync(any(), any(), any(), any(), any(), any()) } answers {
                UtilsMock.getTopAlbumsDeferredResponse()
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
            albumList.forEach {
                UtilsAssertion.assertTopAlbumEntity(it)
            }
        }
    }

    @Test
    fun `get album info result successfully`() {
        val mockedApi = mockk<LastFmApi> {
            every { getAlbumInfoForIdAsync(any(), any(), any(), any(), any()) } answers {
                UtilsMock.getAlbumInfoDeferredResponse()
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
            UtilsAssertion.assertAlbumEntity(bodyResponse.album)
        }
    }

}