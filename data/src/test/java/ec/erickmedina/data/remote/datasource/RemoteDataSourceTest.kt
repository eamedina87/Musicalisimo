package ec.erickmedina.data.remote.datasource


import com.google.common.truth.Truth.assertThat
import ec.erickmedina.data.remote.client.LastFmApi
import ec.erickmedina.data.remote.client.RemoteClient
import ec.erickmedina.data.utils.UtilsAssertion
import ec.erickmedina.data.utils.UtilsMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoteDataSourceTest {

    lateinit var remoteClient: RemoteClient
    lateinit var api: LastFmApi

    @Before
    fun setupTest() {
        api = mockk {
            every { searchArtistAsync(any(), any(), any(), any(), any(), any()) } answers {
                UtilsMock.getSearchArtistDeferredResponse()
            }
            every { getAlbumInfoForIdAsync(any(), any(), any(), any(), any()) } answers {
                UtilsMock.getAlbumInfoDeferredResponse()
            }
            every { getTopAlbumsForArtistIdAsync(any(), any(), any(), any(), any(), any()) } answers {
                UtilsMock.getTopAlbumsDeferredResponse()
            }
        }
        remoteClient = mockk {
            every { getRemoteCaller() } returns api
        }
    }

    @Test
    fun `search artist with success`() {
        runBlocking {
            val dataSource = RemoteDataSourceImpl(remoteClient)
            val matches = dataSource.searchArtistAsync("")
            verifySequence {
                remoteClient.getRemoteCaller()
                api.searchArtistAsync(any(), any(), any(), any(), any(), any())
            }
            assertThat(matches).isNotNull()
            assertThat(matches.artist).isNotEmpty()
            matches.artist.forEach {
                UtilsAssertion.assertArtistEntity(it)
            }
        }
    }

    @Test
    fun `search artist with error`() {
        val api = mockk<LastFmApi> {
            every { searchArtistAsync(any(), any(), any(), any(), any(), any()) } throws Exception("remote exception")
        }
        val remoteClient = mockk<RemoteClient> {
            every { getRemoteCaller() } returns api
        }

        runBlocking {
            try {
                val dataSource = RemoteDataSourceImpl(remoteClient)
                dataSource.searchArtistAsync("")
            } catch (e: java.lang.Exception) {
                assertThat(e.message).matches("remote exception")
            }
            verifySequence {
                remoteClient.getRemoteCaller()
                api.searchArtistAsync(any(), any(), any(), any(), any(), any())
            }
        }
    }

    @Test
    fun `get top albums result successfully`() {
        runBlocking {
            val dataSource = RemoteDataSourceImpl(remoteClient)
            val topAlbums = dataSource.getTopAlbumsForArtistIdAsync("")
            verifySequence {
                remoteClient.getRemoteCaller()
                api.getTopAlbumsForArtistIdAsync(any(), any(), any(), any(), any(), any())
            }
            assertThat(topAlbums).isNotNull()
            assertThat(topAlbums.album).isNotEmpty()
            topAlbums.album.forEach {
                UtilsAssertion.assertTopAlbumEntity(it)
            }
        }
    }

    @Test
    fun `get top albums with error`() {
        val api = mockk<LastFmApi> {
            every { getTopAlbumsForArtistIdAsync(any(), any(), any(), any(), any(), any()) } throws Exception("remote exception")
        }
        val remoteClient = mockk<RemoteClient> {
            every { getRemoteCaller() } returns api
        }

        runBlocking {
            try {
                val dataSource = RemoteDataSourceImpl(remoteClient)
                dataSource.getTopAlbumsForArtistIdAsync("")
            } catch (e: java.lang.Exception) {
                assertThat(e.message).matches("remote exception")
            }
            verifySequence {
                remoteClient.getRemoteCaller()
                api.getTopAlbumsForArtistIdAsync(any(), any(), any(), any(), any(), any())
            }
        }
    }

    @Test
    fun `get album info result successfully`() {
        runBlocking {
            val dataSource = RemoteDataSourceImpl(remoteClient)
            val album = dataSource.getAlbumInfoForId("", "")
            verifySequence {
                remoteClient.getRemoteCaller()
                api.getAlbumInfoForIdAsync(any(), any(), any(), any(), any())
            }
            assertThat(album).isNotNull()
            UtilsAssertion.assertAlbumEntity(album)
        }
    }

    @Test
    fun `get album info with error`() {
        val api = mockk<LastFmApi> {
            every { getAlbumInfoForIdAsync(any(), any(), any(), any(), any()) } throws Exception("remote exception")
        }
        val remoteClient = mockk<RemoteClient> {
            every { getRemoteCaller() } returns api
        }

        runBlocking {
            try {
                val dataSource = RemoteDataSourceImpl(remoteClient)
                dataSource.getAlbumInfoForId("", "")
            } catch (e: java.lang.Exception) {
                assertThat(e.message).matches("remote exception")
            }
            verifySequence {
                remoteClient.getRemoteCaller()
                api.getAlbumInfoForIdAsync(any(), any(), any(), any(), any())
            }
        }
    }
}