package ec.erickmedina.domain.usecase

import com.google.common.truth.Truth
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

class TopAlbumsUseCaseTest {
    lateinit var repository: Repository

    @Before
    fun setupTest() {
        repository = mockk {
            coEvery { getTopAlbumsForArtist(any()) } returns UtilsMock.getMockedTopAlbumList()
        }
    }

    @Test
    fun `use case gets top albums with success`() {
        runBlocking {
            val useCase = TopAlbumsUseCase(repository)
            val state = useCase.execute(TopAlbumsUseCase.Params("jba"))
            coVerify { repository.getTopAlbumsForArtist(any()) }
            assert(state is DataState.Success)
            val topAlmbums = (state as DataState.Success).data
            assertThat(topAlmbums).isNotEmpty()
            topAlmbums.forEach {
                UtilsAssertion.assertTopAlbumModel(it)
            }
        }
    }

    @Test
    fun `use case gets top albums with null params gives error`() {
        runBlocking {
            val useCase = TopAlbumsUseCase(repository)
            val state = useCase.execute()
            coVerify(exactly = 0) { repository.getTopAlbumsForArtist(any()) }
            assert(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("Artist name or id must be specified")
        }
    }

    @Test
    fun `use case gets top albums with empty input gives error`() {
        runBlocking {
            val useCase = TopAlbumsUseCase(repository)
            val state = useCase.execute(TopAlbumsUseCase.Params(""))
            coVerify(exactly = 0) { repository.getTopAlbumsForArtist(any()) }
            assert(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("Artist name or id must be specified")
        }
    }

    @Test
    fun `use case gets top albums info with error`() {
        val mRepository = mockk <Repository> {
            coEvery { getTopAlbumsForArtist(any()) } throws NoConnectivityException("No internet")
        }
        runBlocking {
            val useCase = TopAlbumsUseCase(mRepository)
            val state = useCase.execute(TopAlbumsUseCase.Params("jba"))
            coVerify { mRepository.searchArtistWithInput(any()) }
            assert(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("No internet")
        }
    }
}