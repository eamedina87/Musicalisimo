package ec.erickmedina.musicalisimo.ui.albums.detail

import com.google.common.truth.Truth.assertThat
import ec.erickmedina.domain.states.AlbumAction
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.domain.usecase.AlbumInfoUseCase
import ec.erickmedina.domain.usecase.SaveOrDeleteAlbumUseCase
import ec.erickmedina.musicalisimo.common.BaseTest
import ec.erickmedina.musicalisimo.utils.UtilsAssertion
import ec.erickmedina.musicalisimo.utils.UtilsMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AlbumDetailViewModelTest: BaseTest() {

    private lateinit var albumInfoUseCase: AlbumInfoUseCase
    private lateinit var albumSaveOrDeleteUseCase: SaveOrDeleteAlbumUseCase

    @Before
    fun setupTest() {
        albumInfoUseCase = mockk {
            coEvery { execute(any()) } returns DataState.Success(UtilsMock.getAlbumOne())
        }
        albumSaveOrDeleteUseCase = mockk {
            coEvery { execute(any()) } returns true
        }
    }

    @Test
    fun `album info executes with success`() {
        val viewModel = AlbumDetailViewModel(albumInfoUseCase, albumSaveOrDeleteUseCase)
        viewModel.getAlbumObservable().observeForever {  }
        viewModel.getAlbumInfoFor("oasis")
        coVerify { albumInfoUseCase.execute(any()) }
        coVerify (exactly = 0) { albumSaveOrDeleteUseCase.execute(any()) }
        val state = viewModel.getAlbumObservable().value
        assertThat(state is DataState.Success)
        val album = (state as DataState.Success).data
        assertThat(album).isNotNull()
        UtilsAssertion.assertAlbumModel(album)
    }

    @Test
    fun `album info executes with exception`() {
        val mAlbumInfoUseCase = mockk<AlbumInfoUseCase> {
            coEvery { execute(any()) } returns DataState.Error("remote exception")
        }
        val viewModel = AlbumDetailViewModel(mAlbumInfoUseCase, albumSaveOrDeleteUseCase)
        viewModel.getAlbumObservable().observeForever {  }
        viewModel.getAlbumInfoFor("oasis")
        coVerify { mAlbumInfoUseCase.execute(any()) }
        coVerify (exactly = 0) { albumSaveOrDeleteUseCase.execute(any()) }
        val state = viewModel.getAlbumObservable().value
        assertThat(state is DataState.Error)
        val error = (state as DataState.Error).error.toString()
        assertThat(error).matches("remote exception")
    }

    @Test
    fun `save album executes with success`() {
        val viewModel = AlbumDetailViewModel(albumInfoUseCase, albumSaveOrDeleteUseCase)
        viewModel.getAlbumActionObservable().observeForever {  }
        val album = UtilsMock.getAlbumOne()
        viewModel.saveAlbum(album)
        coVerify { albumSaveOrDeleteUseCase.execute(SaveOrDeleteAlbumUseCase.Params(album, false)) }
        coVerify (exactly = 0) { albumInfoUseCase.execute(any()) }
        val pair = viewModel.getAlbumActionObservable().value
        assertThat(pair).isNotNull()
        assertThat(pair!!.first).isEqualTo(AlbumAction.Save)
        assertThat(pair.second).isTrue()
    }

    @Test
    fun `save album executes with error`() {
        val mAlbumSaveOrDeleteUseCase = mockk<SaveOrDeleteAlbumUseCase> {
            coEvery { execute(any()) } returns false
        }
        val viewModel = AlbumDetailViewModel(albumInfoUseCase, mAlbumSaveOrDeleteUseCase)
        viewModel.getAlbumActionObservable().observeForever {  }
        val album = UtilsMock.getAlbumOne()
        viewModel.saveAlbum(album)
        coVerify { mAlbumSaveOrDeleteUseCase.execute(SaveOrDeleteAlbumUseCase.Params(album, false)) }
        coVerify (exactly = 0) { albumInfoUseCase.execute(any()) }
        val pair = viewModel.getAlbumActionObservable().value
        assertThat(pair).isNotNull()
        assertThat(pair!!.first).isEqualTo(AlbumAction.Save)
        assertThat(pair.second).isFalse()
    }

    @Test
    fun `delete album executes with success`() {
        val viewModel = AlbumDetailViewModel(albumInfoUseCase, albumSaveOrDeleteUseCase)
        viewModel.getAlbumActionObservable().observeForever {  }
        val album = UtilsMock.getAlbumOne()
        viewModel.deleteAlbum(album)
        coVerify { albumSaveOrDeleteUseCase.execute(SaveOrDeleteAlbumUseCase.Params(album, true)) }
        coVerify (exactly = 0) { albumInfoUseCase.execute(any()) }
        val pair = viewModel.getAlbumActionObservable().value
        assertThat(pair).isNotNull()
        assertThat(pair!!.first).isEqualTo(AlbumAction.Delete)
        assertThat(pair.second).isTrue()
    }

    @Test
    fun `delete album executes with exception`() {
        val mAlbumSaveOrDeleteUseCase = mockk<SaveOrDeleteAlbumUseCase> {
            coEvery { execute(any()) } returns false
        }
        val viewModel = AlbumDetailViewModel(albumInfoUseCase, mAlbumSaveOrDeleteUseCase)
        viewModel.getAlbumActionObservable().observeForever {  }
        val album = UtilsMock.getAlbumOne()
        viewModel.deleteAlbum(album)
        coVerify { mAlbumSaveOrDeleteUseCase.execute(SaveOrDeleteAlbumUseCase.Params(album, true)) }
        coVerify (exactly = 0) { albumInfoUseCase.execute(any()) }
        val pair = viewModel.getAlbumActionObservable().value
        assertThat(pair).isNotNull()
        assertThat(pair!!.first).isEqualTo(AlbumAction.Delete)
        assertThat(pair.second).isFalse()
    }

}