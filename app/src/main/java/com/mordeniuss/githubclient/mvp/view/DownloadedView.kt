package com.mordeniuss.githubclient.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface DownloadedView: MvpView {
    fun init()
    fun updateAdapter()
    fun openFile(uri: String)
}