package com.mordeniuss.githubclient.mvp.model.repo

import com.mordeniuss.githubclient.mvp.model.datasource.RepositoryLocalDataSource
import com.mordeniuss.githubclient.mvp.model.datasource.RepositoryRemoteDataSource
import com.mordeniuss.githubclient.mvp.model.entity.Pagination
import com.mordeniuss.githubclient.mvp.model.entity.Repository
import com.mordeniuss.githubclient.mvp.model.entity.exception.NoInternetException
import com.mordeniuss.githubclient.mvp.model.file.FileManager
import com.mordeniuss.githubclient.mvp.model.network.NetworkStatus
import com.mordeniuss.githubclient.ui.file.AndroidFileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepositoryRepo(
    val local: RepositoryLocalDataSource,
    val remote: RepositoryRemoteDataSource,
    val fileManager: FileManager,
    val networkStatus: NetworkStatus
) {
    suspend fun search(query: String, page: Int = 1): Result<Pagination<Repository>> = withContext(Dispatchers.IO) {
        val result: Result<Pagination<Repository>>
        if (networkStatus.isOnline()) {
            val pag = remote.search(query, page)
            pag.items.forEach {
                val localRepo = local.getById(it.id)
                it.isDownloaded = localRepo?.isDownloaded ?: false
            }
            result = Result.success(pag)
        } else {
            result = Result.failure(NoInternetException())
        }
        Timber.d("search [$query] found $result")
        result
    }

    suspend fun getLocal(): List<Repository> = withContext(Dispatchers.IO) {
        local.getAll()
    }

    suspend fun download(repository: Repository) = withContext(Dispatchers.IO) {
        val bytes = remote.download(repository)
        fileManager.writeToDownloads("${repository.owner.login}-${repository.name}", bytes)
        repository.isDownloaded = true
        local.save(repository)
    }
}