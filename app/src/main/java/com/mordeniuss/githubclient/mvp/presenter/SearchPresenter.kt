package com.mordeniuss.githubclient.mvp.presenter

import com.mordeniuss.githubclient.mvp.model.entity.Pagination
import com.mordeniuss.githubclient.mvp.model.entity.Repository
import com.mordeniuss.githubclient.mvp.model.repo.RepositoryRepo
import com.mordeniuss.githubclient.mvp.presenter.list.IUserListPresenter
import com.mordeniuss.githubclient.mvp.view.SearchView
import com.mordeniuss.githubclient.mvp.view.item.RepositoryItemView
import com.mordeniuss.githubclient.navigation.Screens
import com.mordeniuss.githubclient.utils.debounce
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class SearchPresenter : MvpPresenter<SearchView>() {

    @Inject
    lateinit var repositoryRepo: RepositoryRepo

    @Inject
    lateinit var router: Router

    val onTextChangedDebounced = debounce(500L, presenterScope, ::onTextChanged)

    var isLoading: Boolean = false
    var curPagination: Pagination<Repository>? = null
    var curSearchQuery: String? = null

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

        override fun onDownloadBtnClicked(view: RepositoryItemView) {
            presenterScope.launch {
                val repo = repos[view.pos]
                repositoryRepo.download(repo)
                view.setDownloaded(repo.isDownloaded)
            }
        }

        override fun onOpenDownloadsBtnClicked(pos: Int) {
            router.navigateTo(Screens.DownloadsScreen())
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun onTextChanged(text: String) = presenterScope.launch {
        Timber.d("on text changed [$text]")
        if (text.isEmpty())
            return@launch
        curSearchQuery = text
        val result = repositoryRepo.search(curSearchQuery!!)
        result.fold({
            curPagination = it
            listPresenter.repos.clear()
            listPresenter.repos.addAll(curPagination!!.items)
            viewState.updateAdapter()
        }, {

        })
    }

    fun onScrollLimit() = presenterScope.launch {
        if (!isLoading && curPagination != null && curPagination?.nextPage() != null && curSearchQuery != null) {
            isLoading = true
            Timber.d("on scroll limit, loading")
            val result = repositoryRepo.search(curSearchQuery!!, curPagination!!.nextPage()!!)
            result.fold({
                curPagination = it
                listPresenter.repos.addAll(curPagination!!.items)
                viewState.updateAdapter()
                isLoading = false
            }, {
                isLoading = false
            })
        }
    }
}