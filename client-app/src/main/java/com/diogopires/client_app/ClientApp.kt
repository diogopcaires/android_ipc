package com.diogopires.client_app

import android.app.Application
import com.diogopires.client_app.data.RepositoryImpl
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin

class ClientApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(listOf(getModule()))
    }

    private fun getModule() = module {
        factory { RepositoryImpl(applicationContext) }
    }
}