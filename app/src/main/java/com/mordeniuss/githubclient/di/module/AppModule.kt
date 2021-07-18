package com.mordeniuss.githubclient.di.module

import com.mordeniuss.githubclient.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App {
        return app
    }

}
