package com.example.githubusersearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersearch.R
import com.example.githubusersearch.utils.GlideRequests

class LoadingIndicatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): LoadingIndicatorViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.github_users_loading_indicator_view_holder, parent, false)
            return LoadingIndicatorViewHolder(view)
        }
    }
}