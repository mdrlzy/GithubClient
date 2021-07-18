package com.mordeniuss.githubclient.di.module

import com.mordeniuss.githubclient.mvp.model.file.FileManager
import com.mordeniuss.githubclient.mvp.model.network.NetworkStatus
import com.mordeniuss.githubclient.ui.App
import com.mordeniuss.githubclient.ui.file.AndroidFileManager
import com.mordeniuss.githubclient.ui.network.AndroidNetworkStatus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule {
    @Singleton
    @Provides
    fun networkStatus(app: App): NetworkStatus {
        return AndroidNetworkStatus(app)
    }

    @Singleton
    @Provides
    fun fileManager(app: App): FileManager {
        return AndroidFileManager(app)
    }
}