package com.loginbaubap.app

import android.app.Application
import com.loginbaubap.BuildConfig
import com.loginbaubap.app.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                viewModelsModule,
                useCasesModule,
                repositoriesModule,
                remoteDataSourceModule,
                localDataSourceModule
            )
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + ':' + element.lineNumber + '#' + element.methodName + "()"
                }
            })
        }
    }
}