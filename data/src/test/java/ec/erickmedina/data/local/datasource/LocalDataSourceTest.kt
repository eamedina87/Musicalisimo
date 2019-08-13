package ec.erickmedina.data.local.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import ec.erickmedina.data.local.database.MusicalisimoDatabase
import ec.erickmedina.data.local.database.dao.AlbumDao
import ec.erickmedina.data.local.database.entity.DatabaseEntities
import ec.erickmedina.data.utils.UtilsMock
import ec.erickmedina.domain.states.AlbumFilter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LocalDataSourceTest {

    lateinit var mDatabase : MusicalisimoDatabase
    lateinit var mDao : AlbumDao

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {
        mDao = mockk {
            every { insertAlbum(any()) } returns 1
            every { deleteAlbum(any()) } returns 1
            every { getAllAlbums() } returns  UtilsMock.getMockedLiveDatabaseAlbumList()
            every { getAllAlbumsByName() } returns UtilsMock.getMockedLiveDatabaseAlbumList()
            every { getAllAlbumsByArtist() } returns UtilsMock.getMockedLiveDatabaseAlbumList()
            every { getAllAlbumsByPlaycount() } returns UtilsMock.getMockedLiveDatabaseAlbumList()
        }
        mDatabase = mockk {
            every { albumDao() } returns mDao
        }
    }

    @Test
    fun `retrieve saved albums with filter ID correctly`(){
        runBlocking {
            val dataSource = LocalDataSourceImpl(mDatabase)
            val albums = dataSource.getSavedAlbums()
            albums.observeForever {  }
            verifySequence {
                mDatabase.albumDao()
                mDao.getAllAlbums()
            }
            verify (exactly = 0){
                mDao.getAllAlbumsByArtist()
                mDao.getAllAlbumsByName()
                mDao.getAllAlbumsByPlaycount()
            }
            assertThat(albums).isNotNull()
            assertThat(albums.value).isNotEmpty()
            albums.value!!.forEach {
                assertAlbum(it)
            }
        }
    }

    @Test
    fun `retrieve saved albums with filter ARTIST correctly`(){
        runBlocking {
            val dataSource = LocalDataSourceImpl(mDatabase)
            val albums = dataSource.getSavedAlbums(AlbumFilter.Artist)
            verifySequence {
                mDatabase.albumDao()
                mDao.getAllAlbumsByArtist()
            }
            verify (exactly = 0){
                mDao.getAllAlbums()
                mDao.getAllAlbumsByName()
                mDao.getAllAlbumsByPlaycount()
            }
            assertThat(albums).isNotNull()
            assertThat(albums.value).isNotEmpty()
            albums.value!!.forEach {
                assertAlbum(it)
            }
        }
    }

    @Test
    fun `retrieve saved albums with filter NAME correctly`(){
        runBlocking {
            val dataSource = LocalDataSourceImpl(mDatabase)
            val albums = dataSource.getSavedAlbums(AlbumFilter.Name)
            verifySequence {
                mDatabase.albumDao()
                mDao.getAllAlbumsByName()
            }
            verify (exactly = 0){
                mDao.getAllAlbums()
                mDao.getAllAlbumsByArtist()
                mDao.getAllAlbumsByPlaycount()
            }
            assertThat(albums).isNotNull()
            assertThat(albums.value).isNotEmpty()
            albums.value!!.forEach {
                assertAlbum(it)
            }
        }
    }

    @Test
    fun `retrieve saved albums with filter PLAYCOUNT correctly`() {
        runBlocking {
            val dataSource = LocalDataSourceImpl(mDatabase)
            val albums = dataSource.getSavedAlbums(AlbumFilter.Playcount)
            verifySequence {
                mDatabase.albumDao()
                mDao.getAllAlbumsByPlaycount()
            }
            verify (exactly = 0){
                mDao.getAllAlbums()
                mDao.getAllAlbumsByName()
                mDao.getAllAlbumsByArtist()
            }
            assertThat(albums).isNotNull()
            assertThat(albums.value).isNotEmpty()
            albums.value!!.forEach {
                assertAlbum(it)
            }
        }
    }

    @Test
    fun `save album correctly`() {
        runBlocking {
            val dataSource = LocalDataSourceImpl(mDatabase)
            val result = dataSource.saveAlbum(UtilsMock.getAlbumOne())
            verifySequence {
                mDatabase.albumDao()
                mDao.insertAlbum(any())
            }
            assertThat(result).isGreaterThan(0)
        }
    }

    @Test
    fun `delete album correctly`() {
        runBlocking {
            val dataSource = LocalDataSourceImpl(mDatabase)
            val result = dataSource.deleteAlbum(UtilsMock.getAlbumOne())
            verifySequence {
                mDatabase.albumDao()
                mDao.deleteAlbum(any())
            }
            assertThat(result).isTrue()
        }
    }


    private fun assertAlbum(it: DatabaseEntities.AlbumEntity) {
        with(it) {
            assertThat(artist).isNotEmpty()
            assertThat(name).isNotEmpty()
            assertThat(playcount).isAtLeast(0)
            assertThat(album).isNotNull()
            with(album) {
                assertThat(name).isNotEmpty()
                assertThat(artist).isNotEmpty()
                assertThat(image).isNotEmpty()
                assertThat(listeners).isAtLeast(0)
                assertThat(playcount).isAtLeast(0)
                assertThat(tracks).isNotNull()
                assertThat(tracks.track).isNotEmpty()
                tracks.track.forEach {
                    with(it) {
                        assertThat(name).isNotEmpty()
                        assertThat(duration).isGreaterThan(0)
                        assertThat(attr).isNotEmpty()
                        assertThat(streamable).isNotEmpty()
                        assertThat(artist).isNotNull()
                    }
                }
            }
        }
    }

}