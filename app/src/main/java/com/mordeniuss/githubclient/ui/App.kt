package com.mordeniuss.githubclient.ui

import android.app.Application
import com.mordeniuss.githubclient.BuildConfig
import com.mordeniuss.githubclient.di.AppComponent
import com.mordeniuss.githubclient.di.DaggerAppComponent
import com.mordeniuss.githubclient.di.module.AppModule
import timber.log.Timber

class App: Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set


    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}