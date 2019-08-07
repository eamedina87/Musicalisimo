package ec.erickmedina.musicalisimo.ui.search


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.domain.usecase.SearchArtistUseCase
import ec.erickmedina.musicalisimo.common.BaseTest
import ec.erickmedina.musicalisimo.utils.UtilsAssertion
import ec.erickmedina.musicalisimo.utils.UtilsMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchViewModelTest : BaseTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `search executes with success`() {
        val useCase = mockk <SearchArtistUseCase> {
            coEvery { execute(any()) } returns DataState.Success(UtilsMock.getMockedArtistList())
        }
        val viewModel = SearchViewModel(useCase)
        viewModel.getArtistListObservable().observeForever {  }
        viewModel.getArtistForInput("jba")
        coVerify { useCase.execute(SearchArtistUseCase.Params("jba")) }
        val state = viewModel.getArtistListObservable().value
        assertThat(state).isNotNull()
        assertThat(state is DataState.Success)
        val artistList = (state as DataState.Success).data
        assertThat(artistList).isNotEmpty()
        artistList.forEach {
            UtilsAssertion.assertArtistModel(it)
        }
    }

    @Test
    fun `search executes with empty input error`() {
        val useCase = mockk <SearchArtistUseCase> {
            coEvery { execute(any()) } returns DataState.Error("input null or empty")
        }
        val viewModel = SearchViewModel(useCase)
        viewModel.getArtistListObservable().observeForever {  }
        viewModel.getArtistForInput("")
        coVerify { useCase.execute(SearchArtistUseCase.Params("")) }
        val state = viewModel.getArtistListObservable().value
        assertThat(state).isNotNull()
        assertThat(state is DataState.Error)
        val error = (state as DataState.Error).error.toString()
        assertThat(error).matches("input null or empty")
    }

    @Test
    fun `search executes with exception`() {
        val useCase = mockk <SearchArtistUseCase> {
            coEvery { execute(any()) } returns DataState.Error("remote exception")
        }
        val viewModel = SearchViewModel(useCase)
        viewModel.getArtistListObservable().observeForever {  }
        viewModel.getArtistForInput("jba")
        coVerify { useCase.execute(SearchArtistUseCase.Params("jba")) }
        val state = viewModel.getArtistListObservable().value
        assertThat(state).isNotNull()
        assertThat(state is DataState.Error)
        val error = (state as DataState.Error).error.toString()
        assertThat(error).matches("remote exception")
    }

}