package com.mordeniuss.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mordeniuss.githubclient.R
import com.mordeniuss.githubclient.mvp.presenter.list.IUserListPresenter
import com.mordeniuss.githubclient.mvp.view.item.RepositoryItemView
import com.mordeniuss.githubclient.utils.loadCircleImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.view.*

class RepositoryRVAdapter(
    val presenter: IUserListPresenter
): RecyclerView.Adapter<RepositoryRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_user,
            parent,
            false
        )
    )

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        presenter.bindView(holder)
        with(holder.containerView) {
            btn_open_browser.setOnClickListener {
                presenter.onBrowserBtnClicked(holder.pos)
            }
            btn_download.setOnClickListener {
                presenter.onDownloadBtnClicked(holder)
            }
            btn_open_downloads.setOnClickListener {
                presenter.onOpenDownloadsBtnClicked(holder.pos)
            }
        }
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, RepositoryItemView {

        override var pos = -1

        override fun setRepoName(name: String) = with(containerView) {
            tv_repo_name.text = name
        }

        override fun setDescription(description: String) = with(containerView) {
            tv_description.text = description
        }

        override fun setLogin(login: String) = with(containerView) {
            tv_login.text = login
        }

        override fun setAvatar(link: String) = with(containerView) {
            loadCircleImage(link, iv_profile_pic)
        }

        override fun setDownloaded(isDownloaded: Boolean) = with(containerView) {
            if (isDownloaded) {
                btn_download.visibility = View.GONE
                btn_open_downloads.visibility = View.VISIBLE
            } else {
                btn_download.visibility = View.VISIBLE
                btn_open_downloads.visibility = View.GONE
            }
        }
    }
}