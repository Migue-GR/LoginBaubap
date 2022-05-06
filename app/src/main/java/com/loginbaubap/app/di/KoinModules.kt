package com.loginbaubap.app.di

import com.loginbaubap.datasource.local.LocalUserDataSource
import com.loginbaubap.datasource.remote.RemoteUserDataSource
import com.loginbaubap.domain.LoginUseCase
import com.loginbaubap.repository.UserRepository
import com.loginbaubap.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { LoginViewModel(get()) }
}

val useCasesModule = module {
    factory { LoginUseCase(get()) }
}

val repositoriesModule = module {
    factory { UserRepository(get(), get()) }
}

val remoteDataSourceModule: Module = module {
    single { RemoteUserDataSource() }
}

val localDataSourceModule = module {
    single { LocalUserDataSource() }
}