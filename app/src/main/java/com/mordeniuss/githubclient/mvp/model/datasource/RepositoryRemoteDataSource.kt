package com.mordeniuss.githubclient.mvp.model.datasource

import com.mordeniuss.githubclient.mvp.model.api.GitHubApi
import com.mordeniuss.githubclient.mvp.model.entity.Pagination
import com.mordeniuss.githubclient.mvp.model.entity.Repository
import com.mordeniuss.githubclient.mvp.model.entity.User
import com.mordeniuss.githubclient.mvp.model.entity.api.ResponsePagination
import com.mordeniuss.githubclient.mvp.model.entity.api.ResponseRepository
import com.mordeniuss.githubclient.mvp.model.entity.api.ResponseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepositoryRemoteDataSource(
    val api: GitHubApi
) {

    suspend fun search(q: String, page: Int): Pagination<Repository> = withContext(Dispatchers.IO) {
        val pag = mapPagFromApi(api.searchRepositories(q, page))
        pag.page = page
        Timber.d("search [$q] found $pag")
        return@withContext pag
    }

    suspend fun download(repo: Repository): ByteArray = withContext(Dispatchers.IO) {
        Timber.d("download $repo")
        val body = api.downloadRepo(repo.owner.login, repo.name)
        body.bytes()
    }

    private fun mapPagFromApi(respPag: ResponsePagination<ResponseRepository>): Pagination<Repository> {
        val repos = respPag.items.map { mapRepositoryFromApi(it) }
        return Pagination(respPag.incomplete_results, repos)
    }

    private fun mapUserFromApi(respUser: ResponseUser): User {
        return User(respUser.id, respUser.login, respUser.avatar_url)
    }

    private fun mapRepositoryFromApi(respRepo: ResponseRepository): Repository {
        return Repository(respRepo.id, respRepo.name, respRepo.description, mapUserFromApi(respRepo.owner), respRepo.html_url, false)
    }

}