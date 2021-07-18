package com.mordeniuss.githubclient.mvp.view.item

interface RepositoryItemView {
    var pos: Int

    fun setLogin(login: String)
    fun setAvatar(link: String)
    fun setRepoName(name: String)
    fun setDescription(description: String)
    fun setDownloaded(isDownloaded: Boolean)
}