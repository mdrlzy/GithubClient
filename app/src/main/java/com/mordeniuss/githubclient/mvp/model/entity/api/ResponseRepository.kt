package com.mordeniuss.githubclient.mvp.model.entity.api

class ResponseRepository(
    val id: Long,
    val name: String,
    val owner: ResponseUser,
    val html_url: String,
    val description: String?
)