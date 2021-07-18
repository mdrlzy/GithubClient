package com.mordeniuss.githubclient.mvp.model.entity.api

data class ResponseUser(
    val id: Long,
    val login: String,
    val avatar_url: String
)