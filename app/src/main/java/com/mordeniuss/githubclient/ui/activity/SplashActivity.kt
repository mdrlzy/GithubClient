package com.mordeniuss.githubclient.ui.activity

import android.os.Bundle
import com.mordeniuss.githubclient.mvp.presenter.SplashPresenter
import com.mordeniuss.githubclient.mvp.view.SplashView
import com.mordeniuss.githubclient.ui.App
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class SplashActivity: MvpAppCompatActivity(), SplashView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    val navigator = SupportAppNavigator(this, -1)

    private val presenter by moxyPresenter {
        SplashPresenter().apply { App.instance.appComponent.inject(this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}