package ec.erickmedina.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import ec.erickmedina.data.utils.UtilsAssertion
import ec.erickmedina.data.utils.UtilsMock
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.AlbumFilter
import ec.erickmedina.domain.states.DataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.Exception

class LocalAlbumsUseCaseTest {
    lateinit var repository: Repository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {
        repository = mockk {
            coEvery { getLocalAlbums(any()) } returns UtilsMock.getMockedLiveAlbumList()
        }
    }

    @Test
    fun `use case gets local albums with no filter success`() {
        runBlocking {
            val usecase = LocalAlbumsUseCase(repository)
            val state = usecase.execute()
            coVerify { repository.getLocalAlbums(any()) }
            assertThat(state).isNotNull()
            assertThat(state is DataState.Success)
            val data = (state as DataState.Success).data
            assertThat(data).isNotNull()
            data.observeForever {  }
            val albums = data.value
            assertThat(albums).isNotEmpty()
            albums?.forEach {
                UtilsAssertion.assertAlbumModel(it)
            }
        }
    }

    @Test
    fun `use case gets local albums with filter success`() {
        runBlocking {
            val usecase = LocalAlbumsUseCase(repository)
            val state = usecase.execute(LocalAlbumsUseCase.Params(AlbumFilter.Name))
            coVerify { repository.getLocalAlbums(any()) }
            assertThat(state).isNotNull()
            assertThat(state is DataState.Success)
            val data = (state as DataState.Success).data
            assertThat(data).isNotNull()
            data.observeForever {  }
            val albums = data.value
            assertThat(albums).isNotEmpty()
            albums?.forEach {
                UtilsAssertion.assertAlbumModel(it)
            }
        }
    }

    @Test
    fun `use case gets local albums with no data`() {
        val mRepository = mockk <Repository> {
            coEvery { getLocalAlbums(any()) } returns MutableLiveData()
        }
        runBlocking {
            val usecase = LocalAlbumsUseCase(mRepository)
            val state = usecase.execute()
            coVerify { mRepository.getLocalAlbums(any()) }
            assertThat(state).isNotNull()
            assertThat(state is DataState.Success)
            val data = (state as DataState.Success).data
            assertThat(data).isNotNull()
            data.observeForever {  }
            val albums = data.value
            assertThat(albums).isNull()
        }
    }

    @Test
    fun `use case gets local albums with error`() {
        val mRepository = mockk <Repository> {
            coEvery { getLocalAlbums(any()) } throws Exception("database error")
        }
        runBlocking {
            val usecase = LocalAlbumsUseCase(mRepository)
            val state = usecase.execute()
            coVerify { mRepository.getLocalAlbums(any()) }
            assertThat(state).isNotNull()
            assertThat(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("database error")
        }
    }

}