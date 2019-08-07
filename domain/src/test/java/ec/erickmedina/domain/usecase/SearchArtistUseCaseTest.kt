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
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchArtistUseCaseTest {

    lateinit var repository: Repository

    @Before
    fun setupTest() {
        repository = mockk {
            coEvery { searchArtistWithInput(any()) } returns UtilsMock.getMockedArtistList()
        }
    }

    @Test
    fun `use case gets artist info with success`() {
        runBlocking {
            val useCase = SearchArtistUseCase(repository)
            val state = useCase.execute(SearchArtistUseCase.Params("he"))
            coVerify { repository.searchArtistWithInput(any()) }
            assert(state is DataState.Success)
            val artists = (state as DataState.Success).data
            assertThat(artists).isNotEmpty()
            artists.forEach {
                UtilsAssertion.assertArtistModel(it)
            }
        }
    }

    @Test
    fun `use case gets artist info with null params gives error`() {
        runBlocking {
            val useCase = SearchArtistUseCase(repository)
            val state = useCase.execute()
            coVerify(exactly = 0) { repository.searchArtistWithInput(any()) }
            assert(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("Artist name must be specified")

        }
    }

    @Test
    fun `use case gets artist info with empty input gives error`() {
        runBlocking {
            val useCase = SearchArtistUseCase(repository)
            val state = useCase.execute(SearchArtistUseCase.Params(""))
            coVerify(exactly = 0) { repository.searchArtistWithInput(any()) }
            assert(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("Artist name must be specified")
        }
    }

    @Test
    fun `use case gets artist info with error`() {
        val mRepository = mockk <Repository> {
            coEvery { searchArtistWithInput(any()) } throws NoConnectivityException("No internet")
        }
        runBlocking {
            val useCase = SearchArtistUseCase(mRepository)
            val state = useCase.execute(SearchArtistUseCase.Params("he"))
            coVerify { mRepository.searchArtistWithInput(any()) }
            assert(state is DataState.Error)
            val error = (state as DataState.Error).error.toString()
            assertThat(error).matches("No internet")
        }
    }

}