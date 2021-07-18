package com.mordeniuss.githubclient.mvp.presenter.list

import com.mordeniuss.githubclient.mvp.view.item.RepositoryItemView

interface IUserListPresenter {
    fun getCount(): Int
    fun bindView(view: RepositoryItemView)
    fun onBrowserBtnClicked(pos: Int)
    fun onDownloadBtnClicked(view: RepositoryItemView)
    fun onOpenDownloadsBtnClicked(pos: Int)
}