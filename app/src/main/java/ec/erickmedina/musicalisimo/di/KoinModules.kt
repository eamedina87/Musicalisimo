package ec.erickmedina.musicalisimo.di

import androidx.room.Room
import ec.erickmedina.data.local.database.MusicalisimoDatabase
import ec.erickmedina.data.local.datasource.LocalDataSource
import ec.erickmedina.data.local.datasource.LocalDataSourceImpl
import ec.erickmedina.data.remote.client.RemoteClient
import ec.erickmedina.data.remote.datasource.RemoteDataSource
import ec.erickmedina.data.remote.datasource.RemoteDataSourceImpl
import ec.erickmedina.data.repository.RepositoryImpl
import ec.erickmedina.domain.repository.Repository
import ec.erickmedina.domain.usecase.*
import ec.erickmedina.musicalisimo.BuildConfig
import ec.erickmedina.musicalisimo.common.Navigator
import ec.erickmedina.musicalisimo.ui.albums.detail.AlbumDetailViewModel
import ec.erickmedina.musicalisimo.ui.albums.list.AlbumListViewModel
import ec.erickmedina.musicalisimo.ui.main.MainViewModel
import ec.erickmedina.musicalisimo.ui.search.SearchPagingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val dbName = "musicalisimo.db"

val appModule = module {
    single { Navigator() }
}

val viewModelModule = module {
    viewModel { SearchPagingViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { AlbumListViewModel(get(), get(), get()) }
    viewModel { AlbumDetailViewModel(get(), get()) }
}

val domainModule = module {
    single { SearchArtistUseCase(get()) }
    single { TopAlbumsUseCase(get()) }
    single { LocalAlbumsUseCase(get()) }
    single { SaveOrDeleteAlbumUseCase(get()) }
    single { AlbumInfoUseCase(get()) }
}

val dataModule = module {
    //REPOSITORY
    single <Repository> { RepositoryImpl(get(), get()) }
    //DATA SOURCES
    single <LocalDataSource> { LocalDataSourceImpl(get()) }
    single <RemoteDataSource> { RemoteDataSourceImpl(get()) }
    //LOCAL
    single { Room.databaseBuilder(androidContext(), MusicalisimoDatabase::class.java, dbName).build() }
    //REMOTE
    single { RemoteClient(androidContext(), BuildConfig.API_URL) }
}
