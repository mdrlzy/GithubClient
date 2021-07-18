package com.mordeniuss.githubclient.di.module

import com.mordeniuss.githubclient.mvp.model.datasource.RepositoryLocalDataSource
import com.mordeniuss.githubclient.mvp.model.datasource.RepositoryRemoteDataSource
import com.mordeniuss.githubclient.mvp.model.file.FileManager
import com.mordeniuss.githubclient.mvp.model.network.NetworkStatus
import com.mordeniuss.githubclient.mvp.model.repo.RepositoryRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun repositoryRepo(
        local: RepositoryLocalDataSource,
        remote: RepositoryRemoteDataSource,
        networkStatus: NetworkStatus,
        fileManager: FileManager
    ): RepositoryRepo {
        return RepositoryRepo(local, remote, fileManager, networkStatus)
    }
}