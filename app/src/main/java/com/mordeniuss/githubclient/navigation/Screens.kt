package com.mordeniuss.githubclient.navigation

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.mordeniuss.githubclient.ui.activity.MainActivity
import com.mordeniuss.githubclient.ui.fragment.DownloadedFragment
import com.mordeniuss.githubclient.ui.fragment.SearchFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class SearchScreen: SupportAppScreen() {
        override fun getFragment() = SearchFragment()
    }

    class DownloadedScreen: SupportAppScreen() {
        override fun getFragment() = DownloadedFragment()
    }

    class MainScreen: SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    class BrowserScreen(val url: String): SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            return intent
        }
    }

    class DownloadsScreen: SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
        }
    }
}