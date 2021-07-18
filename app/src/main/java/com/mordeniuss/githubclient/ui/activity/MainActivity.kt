package com.mordeniuss.githubclient.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mordeniuss.githubclient.R
import com.mordeniuss.githubclient.mvp.presenter.MainPresenter
import com.mordeniuss.githubclient.mvp.view.MainView
import com.mordeniuss.githubclient.ui.App
import com.mordeniuss.githubclient.utils.RequestCode
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import timber.log.Timber
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val presenter by moxyPresenter {
        MainPresenter().apply { App.instance.appComponent.inject(this) }
    }

    val navigator = SupportAppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instance.appComponent.inject(this)
    }

    override fun init() {
        bottom_navigation.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.page_search -> {
                    presenter.onSearchTabClicked()
                    true
                }
                R.id.page_downloaded -> {
                    presenter.onDownloadedTabClicked()
                    true
                }
                else -> true
            }
        }
    }

    override fun requestPerm() {
        val requiredPerms = mutableListOf<String>()
        requiredPerms.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val missingPerms = mutableListOf<String>()
        requiredPerms.forEach {
            if(ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                missingPerms.add(it)
            }
        }

        if (missingPerms.isNotEmpty()) {
            Timber.d("requesting $missingPerms")
            ActivityCompat.requestPermissions(this, missingPerms.toTypedArray(), RequestCode.PERMISSIONS)
        } else
            presenter.permsGranted()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == RequestCode.PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED })
                presenter.permsGranted()
            else
                presenter.permsDenied()
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun setSelectedTab(pos: Int) {
        Timber.d("setting selected tab pos $pos")
        bottom_navigation.menu.getItem(pos).isChecked = true
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

}