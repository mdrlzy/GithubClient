package com.mordeniuss.githubclient.mvp.model.entity

data class Repository(
    val id: Long,
    val name: String,
    val description: String?,
    val owner: User,
    val link: String,
    var isDownloaded: Boolean
)