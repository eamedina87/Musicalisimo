package ec.erickmedina.musicalisimo.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import kotlin.coroutines.CoroutineContext

open class BaseTest: CoroutineScope {

    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    open fun before() {
        MockKAnnotations.init(this)
        setupCoroutines()
    }

    @After
    fun after() {
        clearCoroutines()
    }

    private fun setupCoroutines() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    private fun clearCoroutines() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}