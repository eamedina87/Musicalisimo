package ec.erickmedina.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ec.erickmedina.data.local.datasource.LocalDataSource
import ec.erickmedina.data.remote.datasource.RemoteDataSource
import ec.erickmedina.data.util.NoConnectivityException
import ec.erickmedina.data.utils.UtilsAssertion
import ec.erickmedina.data.utils.UtilsMock
import ec.erickmedina.domain.states.AlbumFilter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.Exception

@RunWith(JUnit4::class)
class RepositoryImplTest {

    lateinit var remoteDataSource : RemoteDataSource
    lateinit var localDataSource: LocalDataSource

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {
        remoteDataSource = mockk {
            coEvery { searchArtistAsync(any(), any()) } coAnswers {
                UtilsMock.getRemoteSourceArtistMatches()
            }
            coEvery { getTopAlbumsForArtistIdAsync(any()) } coAnswers {
                UtilsMock.getRemoteSourceTopAlbums()
            }
            coEvery { getAlbumInfoForId(any(), any()) } coAnswers {
                UtilsMock.getRemoteSourceAlbumInfo()
            }
        }

        localDataSource = mockk {
            coEvery { getSavedAlbums(any()) } coAnswers { UtilsMock.getMockedLiveDatabaseAlbumList() }
            coEvery { saveAlbum(any()) } returns true
            coEvery { deleteAlbum(any()) } returns true
        }
    }


    @Test
    fun `get local albums success`() {
        runBlocking {
            val repository = RepositoryImpl(localDataSource, remoteDataSource)
            val albums = repository.getLocalAlbums(AlbumFilter.Id)
            albums.observeForever {  }
            coVerify { localDataSource.getSavedAlbums(any()) }
            assertThat(albums).isNotNull()
            assertThat(albums.value).isNotNull()
            albums?.value?.forEach {
                UtilsAssertion.assertAlbumModel(it)
            }
        }
    }

    @Test
    fun `save album success`() {
        runBlocking {
            val repository = RepositoryImpl(localDataSource, remoteDataSource)
            val result = repository.saveAlbum(UtilsMock.getAlbumOneModel())
            coVerify {
                localDataSource.saveAlbum(any())
            }
            assertThat(result).isEqualTo(UtilsMock.getAlbumOneModel())
        }
    }

    @Test
    fun `save album failure`() {
        val mLocalDataSource = mockk<LocalDataSource> {
            coEvery { saveAlbum(any()) } returns false
        }
        runBlocking {
            val repository = RepositoryImpl(mLocalDataSource, remoteDataSource)
            val result = repository.saveAlbum(UtilsMock.getAlbumOneModel())
            coVerify {
                mLocalDataSource.saveAlbum(any())
            }
            assertThat(result).isNull()
        }
    }

    @Test
    fun `delete album success`() {
        runBlocking {
            val repository = RepositoryImpl(localDataSource, remoteDataSource)
            val result = repository.deleteAlbum(UtilsMock.getAlbumOneModel())
            coVerify {
                localDataSource.deleteAlbum(any())
            }
            assertThat(result).isEqualTo(UtilsMock.getAlbumOneModel())
        }
    }

    @Test
    fun `delete album failure`() {
        val mLocalDataSource = mockk<LocalDataSource> {
            coEvery { deleteAlbum(any()) } returns false
        }
        runBlocking {
            val repository = RepositoryImpl(mLocalDataSource, remoteDataSource)
            val result = repository.deleteAlbum(UtilsMock.getAlbumOneModel())
            coVerify {
                mLocalDataSource.deleteAlbum(any())
            }
            assertThat(result).isNull()
        }
    }

    @Test
    fun `search artist success`() {
        runBlocking {
            val repository = RepositoryImpl(localDataSource, remoteDataSource)
            val artistList = repository.searchArtistWithInput("")
            coVerify { remoteDataSource.searchArtistAsync(any()) }
            assertThat(artistList).isNotEmpty()
            artistList.forEach {
                UtilsAssertion.assertArtistModel(it)
            }
        }
    }

    @Test
    fun `search artist remote exception`() {
        val mRemoteDataSource = mockk<RemoteDataSource> {
            coEvery { searchArtistAsync(any(), any()) } throws NoConnectivityException("No internet")
        }
        runBlocking {
            val repository = RepositoryImpl(localDataSource, mRemoteDataSource)
            try {
                repository.searchArtistWithInput("")
                coVerify { mRemoteDataSource.searchArtistAsync(any()) }
            } catch (e:Exception) {
                assertThat(e is NoConnectivityException)
            }
        }
    }

    @Test
    fun `get top albums success`() {
        runBlocking {
            val repository = RepositoryImpl(localDataSource, remoteDataSource)
            val topAlbums = repository.getTopAlbumsForArtist("")
            coVerify { remoteDataSource.getTopAlbumsForArtistIdAsync(any()) }
            assertThat(topAlbums).isNotEmpty()
            topAlbums.forEach {
                UtilsAssertion.assertTopAlbumModel(it)
            }
        }
    }

    @Test
    fun `get top albums remote exception`() {
        val mRemoteDataSource = mockk<RemoteDataSource> {
            coEvery { getTopAlbumsForArtistIdAsync(any(), any()) } throws NoConnectivityException("No internet")
        }
        runBlocking {
            val repository = RepositoryImpl(localDataSource, mRemoteDataSource)
            try {
                repository.searchArtistWithInput("")
                coVerify { mRemoteDataSource.getTopAlbumsForArtistIdAsync(any()) }
            } catch (e:Exception) {
                assertThat(e is NoConnectivityException)
            }
        }
    }

    @Test
    fun `get album info success`() {
        runBlocking {
            val repository = RepositoryImpl(localDataSource, remoteDataSource)
            val album = repository.getAlbumInfoForId("")
            coVerify { remoteDataSource.getAlbumInfoForId(any(), any()) }
            assertThat(album).isNotNull()
            UtilsAssertion.assertAlbumModel(album)
        }
    }

    @Test
    fun `get album info remote exception`() {
        val mRemoteDataSource = mockk<RemoteDataSource> {
            coEvery { getAlbumInfoForId(any(), any()) } throws NoConnectivityException("No internet")
        }
        runBlocking {
            val repository = RepositoryImpl(localDataSource, mRemoteDataSource)
            try {
                repository.getAlbumInfoForId("")
                coVerify { mRemoteDataSource.getAlbumInfoForId(any(), any()) }
            } catch (e:Exception) {
                assertThat(e is NoConnectivityException)
            }
        }
    }

}