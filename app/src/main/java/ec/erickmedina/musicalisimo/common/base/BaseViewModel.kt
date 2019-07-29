package ec.erickmedina.musicalisimo.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    val mJob = Job()

    override val coroutineContext: CoroutineContext
        = Dispatchers.Main + mJob


    override fun onCleared() {
        super.onCleared()
        mJob.cancel()
    }
}