package com.mordeniuss.githubclient.mvp.model.file

interface FileManager {
    fun writeToDownloads(name: String, byteArray: ByteArray)
}