package com.mordeniuss.githubclient.mvp.model.api

import com.mordeniuss.githubclient.mvp.model.entity.api.ResponsePagination
import com.mordeniuss.githubclient.mvp.model.entity.api.ResponseRepository
import com.mordeniuss.githubclient.mvp.model.entity.api.ResponseUser
import okhttp3.ResponseBody
import retrofit2.http.*

interface GitHubApi {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 15
    ): ResponsePagination<ResponseRepository>

    @Streaming
    @GET("repos/{owner}/{repo}/zipball/")
    suspend fun downloadRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): ResponseBody
}