package ec.erickmedina.domain.usecase

import com.google.common.truth.Truth.assertThat
import ec.erickmedina.data.utils.UtilsMock
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveOrDeleteAlbumUseCaseTest {

    lateinit var repository: Repository

    @Before
    fun setupTest() {
        repository = mockk {
            val albumSlot = slot<AlbumModel>()
            coEvery { saveAlbum(capture(albumSlot)) } coAnswers {
                albumSlot.captured
            }
            val deleteSlot = slot<AlbumModel>()
            coEvery { deleteAlbum(capture(deleteSlot)) } coAnswers {
                deleteSlot.captured
            }
        }
    }

    @Test
    fun `use case save album with success`() {
        runBlocking {
            val usecase = SaveOrDeleteAlbumUseCase(repository)
            val savedAlbum = usecase.execute(SaveOrDeleteAlbumUseCase.Params(UtilsMock.getAlbumOne()))
            coVerify { repository.saveAlbum(any()) }
            assertThat(savedAlbum).isTrue()
        }
    }

    @Test
    fun `use case save album with null data gives error`() {
        runBlocking {
            val usecase = SaveOrDeleteAlbumUseCase(repository)
            val savedAlbum = usecase.execute()
            coVerify(exactly = 0) { repository.saveAlbum(any()) }
            assertThat(savedAlbum).isFalse()
        }
    }

    @Test
    fun `use case save album with empty data gives error`() {
        runBlocking {
            val usecase = SaveOrDeleteAlbumUseCase(repository)
            val savedAlbum = usecase.execute(SaveOrDeleteAlbumUseCase.Params(UtilsMock.getAlbumEmpty()))
            coVerify(exactly = 0) { repository.saveAlbum(any()) }
            assertThat(savedAlbum).isFalse()
        }
    }

    @Test
    fun `use case save album with error`() {
        val mRepository = mockk<Repository> {
            coEvery { saveAlbum(any()) } throws Exception("database error")
        }
        runBlocking {
            val usecase = SaveOrDeleteAlbumUseCase(mRepository)
            val savedAlbum = usecase.execute(SaveOrDeleteAlbumUseCase.Params(UtilsMock.getAlbumEmpty()))
            coVerify(exactly = 0) { mRepository.saveAlbum(any()) }
            assertThat(savedAlbum).isFalse()
        }
    }

    @Test
    fun `use case delete album with success`() {
        runBlocking {
            val usecase = SaveOrDeleteAlbumUseCase(repository)
            val deletedAlbum = usecase.execute(SaveOrDeleteAlbumUseCase.Params(UtilsMock.getAlbumOne(), true))
            coVerify { repository.deleteAlbum(any()) }
            coVerify(exactly = 0) { repository.saveAlbum(any()) }
            assertThat(deletedAlbum).isTrue()
        }
    }


    @Test
    fun `use case delete album with empty data gives error`() {
        runBlocking {
            val usecase = SaveOrDeleteAlbumUseCase(repository)
            val savedAlbum = usecase.execute(SaveOrDeleteAlbumUseCase.Params(UtilsMock.getAlbumEmpty(), true))
            coVerify(exactly = 0) {
                repository.saveAlbum(any())
                repository.deleteAlbum(any())
            }
            assertThat(savedAlbum).isFalse()
        }
    }

    @Test
    fun `use case delete album with error`() {
        val mRepository = mockk<Repository> {
            coEvery { saveAlbum(any()) } throws Exception("database error")
        }
        runBlocking {
            val usecase = SaveOrDeleteAlbumUseCase(repository)
            val savedAlbum = usecase.execute(SaveOrDeleteAlbumUseCase.Params(UtilsMock.getAlbumEmpty(), true))
            coVerify(exactly = 0) {
                repository.saveAlbum(any())
                repository.deleteAlbum(any())
            }
            assertThat(savedAlbum).isFalse()
        }
    }

}