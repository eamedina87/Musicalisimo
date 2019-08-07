package ec.erickmedina.musicalisimo.di

import ec.erickmedina.musicalisimo.common.Navigator
import org.koin.dsl.module



val appModule = module {
    single { Navigator() }
}

val viewModelModule = module {

}

val domainModule = module {

}

val dataModule = module {

}
