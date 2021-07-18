package com.mordeniuss.githubclient.di.module

import com.mordeniuss.githubclient.mvp.model.api.GitHubApi
import com.mordeniuss.githubclient.mvp.model.datasource.RepositoryLocalDataSource
import com.mordeniuss.githubclient.mvp.model.datasource.RepositoryRemoteDataSource
import com.mordeniuss.githubclient.mvp.model.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun repositoryLocal(db: Database): RepositoryLocalDataSource {
        return RepositoryLocalDataSource(db.repositoryDao(), db.userDao())
    }

    @Singleton
    @Provides
    fun repositoryRemote(api: GitHubApi): RepositoryRemoteDataSource {
        return RepositoryRemoteDataSource(api)
    }
}