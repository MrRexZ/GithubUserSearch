package com.example.githubusersearch.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersearch.R
import com.example.githubusersearch.api.response.GithubUserItem
import com.example.githubusersearch.utils.GlideRequests

class GithubUsersAdapter(private val glideRequests: GlideRequests) : PagedListAdapter<GithubUserItem, RecyclerView.ViewHolder>(USER_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.github_users_view_holder -> GithubUsersViewHolder.create(parent, glideRequests)
            else -> throw IllegalArgumentException("Unknown View Type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.github_users_view_holder -> (holder as GithubUsersViewHolder).bind(getItem(position))
        }
    }

    //TODO: Add functionality to show loader
    override fun getItemViewType(position: Int): Int {
        return R.layout.github_users_view_holder
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<GithubUserItem>() {
            override fun areItemsTheSame(oldItem: GithubUserItem, newItem: GithubUserItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GithubUserItem, newItem: GithubUserItem): Boolean =
                oldItem == newItem
        }
    }
}