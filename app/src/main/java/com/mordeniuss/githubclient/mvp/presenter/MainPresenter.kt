package com.mordeniuss.githubclient.mvp.presenter

import com.mordeniuss.githubclient.mvp.view.MainView
import com.mordeniuss.githubclient.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter: MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.requestPerm()
    }

    fun permsGranted() {
        viewState.init()
        router.replaceScreen(Screens.SearchScreen())
    }

    fun onSearchTabClicked() {
        router.replaceScreen(Screens.SearchScreen())
    }

    fun onDownloadedTabClicked() {
        router.replaceScreen(Screens.DownloadedScreen())
    }

    fun permsDenied() {

    }
}