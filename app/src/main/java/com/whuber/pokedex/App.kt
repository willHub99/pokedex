package com.whuber.pokedex

import android.app.Application
import com.whuber.pokedex.core.appModuleCreateRetrofit
import com.whuber.pokedex.core.appModuleMainActivity
import com.whuber.pokedex.core.appModulePokemonActivity
import com.whuber.pokedex.core.appModulePokemonRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModuleMainActivity + appModulePokemonActivity + appModulePokemonRepository + appModuleCreateRetrofit)
        }

    }

}