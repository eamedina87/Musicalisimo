package ec.erickmedina.domain.usecase

import com.google.common.truth.Truth.assertThat
import ec.erickmedina.data.utils.UtilsAssertion
import ec.erickmedina.data.utils.UtilsMock
import ec.erickmedina.domain.exceptions.NoConnectivityException
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.states.DataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AlbumInfoUseCaseTest {

    lateinit var repository: Repository

    @Before
    fun setupTest() {
        repository = mockk {
            coEvery { getAlbumInfoForId(any()) } returns UtilsMock.getAlbumOne()
        }
    }

    @Test
    fun `use case gets top albums with success`() {
        runBlocking {
            val useCase = AlbumInfoUseCase(repository)
            val state = useCase.execute(AlbumInfoUseCase.Params("asfas"))
            coVerify { repository.getAlbumInfoForId(any()) }
            assert(state is DataState.Success)
            val album = (state as DataState.Success).data
            assertThat(album).isNotNull()
            UtilsAssertion.assertAlbumModel(album)
        }
    }

    @Test
    fun `use case gets top albums with null params gives error`() {
        runBlocking {
            val useCase = AlbumInfoUseCase(repository)
            val state = useCase.execute()
            coVerify(exactly = 0) { repository.getAlbumInfoForId(any()) }
            assert(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("Album name or id must be specified")
        }
    }

    @Test
    fun `use case gets top albums with empty input gives error`() {
        runBlocking {
            val useCase = AlbumInfoUseCase(repository)
            val state = useCase.execute(AlbumInfoUseCase.Params(""))
            coVerify(exactly = 0) { repository.getAlbumInfoForId(any()) }
            assert(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("Album name or id must be specified")
        }
    }

    @Test
    fun `use case gets top albums info with error`() {
        val mRepository = mockk <Repository> {
            coEvery { getAlbumInfoForId(any()) } throws NoConnectivityException("No internet")
        }
        runBlocking {
            val useCase = AlbumInfoUseCase(mRepository)
            val state = useCase.execute(AlbumInfoUseCase.Params("asfas"))
            coVerify { mRepository.getAlbumInfoForId(any()) }
            assert(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("No internet")
        }
    }

}