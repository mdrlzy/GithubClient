package com.mordeniuss.githubclient.mvp.model.entity.api

data class ResponsePagination<T>(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<T>
)