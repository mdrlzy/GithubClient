package com.mordeniuss.githubclient.mvp.presenter

import com.mordeniuss.githubclient.mvp.model.entity.Repository
import com.mordeniuss.githubclient.mvp.model.repo.RepositoryRepo
import com.mordeniuss.githubclient.mvp.presenter.list.IUserListPresenter
import com.mordeniuss.githubclient.mvp.view.DownloadedView
import com.mordeniuss.githubclient.mvp.view.item.RepositoryItemView
import com.mordeniuss.githubclient.navigation.Screens
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class DownloadedPresenter: MvpPresenter<DownloadedView>() {

    @Inject
    lateinit var repositoryRepo: RepositoryRepo

    @Inject
    lateinit var router: Router

    val listPresenter = UserListPresenter()

    inner class UserListPresenter : IUserListPresenter {
        val repos = mutableListOf<Repository>()

        override fun getCount() = repos.size

        override fun bindView(view: RepositoryItemView) {
            val repo = repos[view.pos]
            view.setLogin(repo.owner.login)
            view.setAvatar(repo.owner.avatar)
            view.setRepoName(repo.name)
            view.setDownloaded(repo.isDownloaded)
            repo.description?.let { view.setDescription(it) }
        }

        override fun onBrowserBtnClicked(pos: Int) {
            router.navigateTo(Screens.BrowserScreen(repos[pos].link))
        }

        override fun onDownloadBtnClicked(view: RepositoryItemView) {}

        override fun onOpenDownloadsBtnClicked(pos: Int) {
            router.navigateTo(Screens.DownloadsScreen())
        }

    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        presenterScope.launch {
            val repos = repositoryRepo.getLocal()
            listPresenter.repos.clear()
            listPresenter.repos.addAll(repos)
            viewState.updateAdapter()
        }
    }
}