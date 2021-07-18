package com.mordeniuss.githubclient.mvp.model.entity.api

interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}