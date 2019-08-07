package ec.erickmedina.musicalisimo.ui.albums.list.top

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.domain.usecase.LocalAlbumsUseCase
import ec.erickmedina.domain.usecase.TopAlbumsUseCase
import ec.erickmedina.musicalisimo.common.BaseTest
import ec.erickmedina.musicalisimo.ui.albums.list.AlbumListViewModel
import ec.erickmedina.musicalisimo.utils.UtilsAssertion
import ec.erickmedina.musicalisimo.utils.UtilsMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AlbumListViewModelTest: BaseTest() {

    private lateinit var topAlbumsUseCase: TopAlbumsUseCase
    private lateinit var localAlbumsUseCase: LocalAlbumsUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {
        topAlbumsUseCase = mockk {
            coEvery{ execute(any()) } returns DataState.Success(UtilsMock.getMockedTopAlbumList())
        }
        localAlbumsUseCase = mockk {
            coEvery{ execute() } returns DataState.Success(UtilsMock.getMockedLiveAlbumList())
        }
    }

    @Test
    fun `top albums executes correctly`() {
        val viewModel = AlbumListViewModel(topAlbumsUseCase, localAlbumsUseCase)
        viewModel.getTopAlbumsObservable().observeForever {  }
        viewModel.getTopAlbumsForArtist("jba")
        coVerify { topAlbumsUseCase.execute(any()) }
        coVerify (exactly = 0) { localAlbumsUseCase.execute() }
        val state = viewModel.getTopAlbumsObservable().value
        assertThat(state is DataState.Success)
        val albums = (state as DataState.Success).data
        assertThat(albums).isNotEmpty()
        albums.forEach {
            UtilsAssertion.assertTopAlbumModel(it)
        }
    }

    @Test
    fun `top albums executes with exception`() {
        val mTopAlbumsUseCase = mockk <TopAlbumsUseCase>{
            coEvery{ execute(any()) } returns DataState.Error("remote exception")
        }
        val viewModel = AlbumListViewModel(mTopAlbumsUseCase, localAlbumsUseCase)
        viewModel.getTopAlbumsForArtist("")
        coVerify { mTopAlbumsUseCase.execute(any()) }
        coVerify (exactly = 0) { localAlbumsUseCase.execute() }
        val state = viewModel.getTopAlbumsObservable().value
        assertThat(state is DataState.Error)
        val error = (state as DataState.Error).error.toString()
        assertThat(error).matches("remote exception")
    }

    @Test
    fun `local albums executes correctly`() {
        val viewModel = AlbumListViewModel(topAlbumsUseCase, localAlbumsUseCase)
        viewModel.getLocalAlbumsObservable().observeForever {  }
        viewModel.getLocalAlbums()
        coVerify { localAlbumsUseCase.execute() }
        coVerify (exactly = 0) { topAlbumsUseCase.execute() }
        val state = viewModel.getLocalAlbumsObservable().value
        assertThat(state is DataState.Success)
        val liveData = (state as DataState.Success).data
        liveData.observeForever {  }
        val albums = liveData.value
        assertThat(albums).isNotEmpty()
        albums?.forEach {
            UtilsAssertion.assertAlbumModel(it)
        }
    }

    @Test
    fun `local albums executes with exception`() {
        val mLocalAlbumsUseCase = mockk <LocalAlbumsUseCase> {
            coEvery { execute() } returns DataState.Error("database exception")
        }
        val viewModel = AlbumListViewModel(topAlbumsUseCase, mLocalAlbumsUseCase)
        viewModel.getLocalAlbumsObservable().observeForever {  }
        viewModel.getLocalAlbums()
        coVerify { mLocalAlbumsUseCase.execute() }
        coVerify (exactly = 0) { topAlbumsUseCase.execute() }
        val state = viewModel.getLocalAlbumsObservable().value
        assertThat(state is DataState.Error)
        val error = (state as DataState.Error).error.toString()
        assertThat(error).matches("database exception")
    }
}