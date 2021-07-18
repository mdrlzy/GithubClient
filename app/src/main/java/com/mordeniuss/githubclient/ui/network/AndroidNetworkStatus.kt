package com.mordeniuss.githubclient.ui.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.mordeniuss.githubclient.mvp.model.network.NetworkStatus

class AndroidNetworkStatus(context: Context) : NetworkStatus {

    private var statusSubject = false

    init {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(), object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                statusSubject = true
            }

            override fun onLost(network: Network) {
                statusSubject = false
            }

            override fun onUnavailable() {
                statusSubject = false
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                statusSubject = false
            }
        })
    }

    override fun isOnline() = statusSubject

}