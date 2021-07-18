package com.mordeniuss.githubclient.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mordeniuss.githubclient.R
import com.mordeniuss.githubclient.mvp.presenter.DownloadedPresenter
import com.mordeniuss.githubclient.mvp.view.DownloadedView
import com.mordeniuss.githubclient.ui.App
import com.mordeniuss.githubclient.ui.activity.MainActivity
import com.mordeniuss.githubclient.ui.adapter.RepositoryRVAdapter
import kotlinx.android.synthetic.main.fragment_downloaded.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class DownloadedFragment: MvpAppCompatFragment(), DownloadedView {

    private val presenter by moxyPresenter {
        DownloadedPresenter().apply { App.instance.appComponent.inject(this) }
    }

    var adapter: RepositoryRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_downloaded, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun init() {
        (activity as MainActivity).setSelectedTab(1)
        adapter = RepositoryRVAdapter(presenter.listPresenter)
        rv_repos_downloaded.layoutManager = LinearLayoutManager(context)
        rv_repos_downloaded.adapter = adapter
    }

    override fun openFile(uri: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(uri), "application/zip")
        startActivity(intent)
    }

    override fun updateAdapter() {
        adapter?.notifyDataSetChanged()
    }
}