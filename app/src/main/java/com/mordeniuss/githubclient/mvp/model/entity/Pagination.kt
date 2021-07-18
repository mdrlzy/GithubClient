package com.mordeniuss.githubclient.mvp.model.entity

data class Pagination <T>(
    val isIncomplete: Boolean,
    val items: List<T>,
    var page: Int = 1
) {
    fun nextPage(): Int? = if (!isIncomplete) page+1 else null
}