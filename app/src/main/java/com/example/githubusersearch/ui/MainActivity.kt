package com.example.githubusersearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.example.githubusersearch.R
import com.example.githubusersearch.api.response.GithubUserItem
import com.example.githubusersearch.repository.GithubRepository
import com.example.githubusersearch.utils.GlideApp
import com.example.githubusersearch.viewmodel.SearchUsersViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var searchUsersViewModel: SearchUsersViewModel
    private val glideRequests by lazy { GlideApp.with(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initAdapter()
        //TODO: Remove this debug line below
        searchUsersViewModel.showSearchRes("pikachu")
    }

    private fun initViewModel() {
        searchUsersViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchUsersViewModel(GithubRepository.create()) as T
            }
        }).get(SearchUsersViewModel::class.java)
    }

    private fun initAdapter() {
        val adapter = GithubUsersAdapter(glideRequests)
        github_users_list_rv.adapter = adapter
        searchUsersViewModel.items.observe(this, Observer<PagedList<GithubUserItem>> {
            adapter.submitList(it)
        })
    }
}
