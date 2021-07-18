package com.mordeniuss.githubclient.di

import com.mordeniuss.githubclient.di.module.*
import com.mordeniuss.githubclient.mvp.presenter.DownloadedPresenter
import com.mordeniuss.githubclient.mvp.presenter.MainPresenter
import com.mordeniuss.githubclient.mvp.presenter.SearchPresenter
import com.mordeniuss.githubclient.mvp.presenter.SplashPresenter
import com.mordeniuss.githubclient.ui.activity.MainActivity
import com.mordeniuss.githubclient.ui.activity.SplashActivity
import com.mordeniuss.githubclient.ui.fragment.DownloadedFragment
import com.mordeniuss.githubclient.ui.fragment.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        AndroidModule::class,
        CiceroneModule::class,
        DatabaseModule::class,
        DataSourceModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    fun inject(mainPresenter: MainPresenter)
    fun inject(mainActivity: MainActivity)
    fun inject(searchFragment: SearchFragment)
    fun inject(searchPresenter: SearchPresenter)
    fun inject(downloadedFragment: DownloadedFragment)
    fun inject(downloadedPresenter: DownloadedPresenter)
    fun inject(splashActivity: SplashActivity)
    fun inject(splashPresenter: SplashPresenter)
}