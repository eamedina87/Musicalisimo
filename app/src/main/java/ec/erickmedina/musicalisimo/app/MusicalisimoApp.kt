package ec.erickmedina.musicalisimo.app

import android.app.Application
import ec.erickmedina.musicalisimo.di.appModule
import ec.erickmedina.musicalisimo.di.dataModule
import ec.erickmedina.musicalisimo.di.domainModule
import ec.erickmedina.musicalisimo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MusicalisimoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MusicalisimoApp)
            loadKoinModules(listOf(appModule, viewModelModule, domainModule, dataModule))
        }
    }

}