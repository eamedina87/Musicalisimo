package ec.erickmedina.data.local.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.data.local.database.entity.DatabaseEntities
import ec.erickmedina.data.util.MockUtils
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MusicalisimoDatabaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    lateinit var mDatabase : MusicalisimoDatabase

    @Before
    fun setupTest() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mDatabase = Room.inMemoryDatabaseBuilder(context, MusicalisimoDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun finishTest() {
        mDatabase.close()
    }

    @Test
    fun albumInsertedCorrectly() {
        val album = MockUtils.getMockedAlbum()
        val id = mDatabase.albumDao().insertAlbum(album)
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun albumRetrievedCorrectly() {
        val album = MockUtils.getMockedAlbum()
        mDatabase.albumDao().insertAlbum(album)
        mDatabase.albumDao().insertAlbum(album)
        mDatabase.albumDao().insertAlbum(album)
        val albumsLive = mDatabase.albumDao().getAllAlbums()
        albumsLive.observeForever {  }
        val albums = albumsLive.value
        assertThat(albums).hasSize(3)

    }

    @Test
    fun albumDeletedCorrectly() {
        val album = MockUtils.getMockedAlbum()
        val id = mDatabase.albumDao().insertAlbum(album)
        var albumsLive = mDatabase.albumDao().getAllAlbums()
        albumsLive.observeForever {  }
        var albums = albumsLive.value
        assertThat(albums).hasSize(1)
        album.id = id
        mDatabase.albumDao().deleteAlbum(album)
        albumsLive = mDatabase.albumDao().getAllAlbums()
        albumsLive.observeForever {  }
        albums = albumsLive.value
        assertThat(albums).isEmpty()
    }



}