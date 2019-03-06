package com.example.githubusersearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersearch.R
import com.example.githubusersearch.api.response.GithubUserItem
import com.example.githubusersearch.utils.GlideRequests

class GithubUsersViewHolder(view: View, val glideRequests: GlideRequests) : RecyclerView.ViewHolder(view) {
    private val userImage: ImageView = view.findViewById(R.id.github_user_image)
    private val usernameTitle: TextView = view.findViewById(R.id.github_username_title)
    private var githubUserItem: GithubUserItem? = null

    fun bind(item: GithubUserItem?) {
        this.githubUserItem = item
        usernameTitle.text = githubUserItem?.login
        if (item?.avatarUrl?.startsWith("http") == true) {
            userImage.visibility = View.VISIBLE
            glideRequests.load(item.avatarUrl).centerCrop().into(userImage)
        } else {
            userImage.visibility = View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup, glideRequests: GlideRequests): GithubUsersViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.github_users_view_holder, parent, false)
            return GithubUsersViewHolder(view, glideRequests)
        }
    }
}