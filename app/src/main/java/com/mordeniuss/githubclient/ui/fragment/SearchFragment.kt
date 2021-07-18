package com.mordeniuss.githubclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.mordeniuss.githubclient.R
import com.mordeniuss.githubclient.ui.App
import com.mordeniuss.githubclient.ui.activity.MainActivity
import com.mordeniuss.githubclient.mvp.presenter.SearchPresenter
import com.mordeniuss.githubclient.mvp.view.SearchView
import com.mordeniuss.githubclient.ui.adapter.RepositoryRVAdapter
import com.mordeniuss.githubclient.utils.addOnScrollLimitListener
import kotlinx.android.synthetic.main.fragment_search.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import timber.log.Timber

class SearchFragment: MvpAppCompatFragment(), SearchView {

    private val presenter by moxyPresenter {
        SearchPresenter().apply { App.instance.appComponent.inject(this) }
    }

    var adapter: RepositoryRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun init() {
        (activity as MainActivity).setSelectedTab(0)
        et_search.addTextChangedListener {
            Timber.d("da")
            presenter.onTextChangedDebounced(it.toString())
        }
        adapter = RepositoryRVAdapter(presenter.listPresenter)
        val layoutManager = LinearLayoutManager(context)
        rv_users.layoutManager = layoutManager
        rv_users.adapter = adapter
        rv_users.addOnScrollLimitListener(layoutManager) {
            presenter.onScrollLimit()
        }
    }

    override fun updateAdapter() {
        adapter?.notifyDataSetChanged()
    }
}




