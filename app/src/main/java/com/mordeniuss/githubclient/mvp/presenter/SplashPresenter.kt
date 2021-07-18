package com.mordeniuss.githubclient.mvp.presenter

import com.mordeniuss.githubclient.mvp.view.SplashView
import com.mordeniuss.githubclient.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SplashPresenter: MvpPresenter<SplashView>() {
    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        presenterScope.launch {
            someInit()
            router.replaceScreen(Screens.MainScreen())
        }
    }

    private suspend fun someInit() {
        delay(2000)
    }
}