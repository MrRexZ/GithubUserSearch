package com.example.githubusersearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.example.githubusersearch.R
import com.example.githubusersearch.api.github.response.GithubUserItem
import com.example.githubusersearch.repository.GithubRepository
import com.example.githubusersearch.utils.GlideApp
import com.example.githubusersearch.viewmodel.SearchUsersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.githubusersearch.GithubApp
import com.example.githubusersearch.api.github.GithubApiException
import com.example.githubusersearch.di.createViewModel
import com.example.githubusersearch.di.github.GithubModule
import com.example.githubusersearch.vo.NetworkState
import com.example.githubusersearch.vo.Status
import kotlinx.android.synthetic.main.error_view.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var searchUsersViewModel: SearchUsersViewModel
    private val glideRequests by lazy { GlideApp.with(this) }
    private lateinit var adapter: GithubUsersAdapter
    val component by lazy {
        (application as GithubApp).component.plus(GithubModule(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
        showWelcomePage()
        initViewModel()
        initPageTypeObsv()
        initAdapter()
        initRecyclerViewUI()
        initRefreshListener()
        initKeywordSearchListener()

    }

    private fun showEmptyListPage() {
        content_view_flipper.displayedChild = 1
    }

    private fun showListPage() {
        content_view_flipper.displayedChild = 0
    }

    private fun showWelcomePage() {
        content_view_flipper.displayedChild = 2
    }

    private fun showErrorPage(mainErrorText: String) {
        content_view_flipper.displayedChild = 3
        error_view.main_error_text.text = mainErrorText
    }

    private fun initPageTypeObsv() {
        searchUsersViewModel.showEmptyUserList.observe(this, Observer { shouldShowEmptyPage ->
            if (shouldShowEmptyPage) {
                showEmptyListPage()
            } else {
                showListPage()
            }
        })
    }

    private fun searchUser(retry: Boolean) {
        if (user_search_view.query.toString().isEmpty()) {
            handleNoTextSearch()
        } else {
            if (retry) {
                searchUsersViewModel.retrySearch()
            } else {
                searchUsersViewModel.showSearchRes(user_search_view.query.toString())
            }
        }
    }

    private fun handleNoTextSearch() {
        Toast.makeText(this, getString(R.string.input_text_to_search), Toast.LENGTH_LONG).show()
        triggerLoader(NetworkState.LOADED)
    }

    private fun initKeywordSearchListener() {
        user_search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUser(false)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun initRefreshListener() {
        github_user_list_swipe_to_refresh.setOnRefreshListener {
            searchUser(true)
        }
    }

    private fun initRecyclerViewUI() {
        val decoration = DividerItemDecoration(applicationContext, VERTICAL)
        github_users_list_rv.addItemDecoration(decoration)
    }

    private fun initViewModel() {
        searchUsersViewModel = createViewModel { component.searchUsersViewModel() }
    }

    private fun initAdapter() {
        adapter = GithubUsersAdapter(glideRequests)
        github_users_list_rv.adapter = adapter
        searchUsersViewModel.items.observe(this, Observer<PagedList<GithubUserItem>> {
            adapter.submitList(it)
        })

        searchUsersViewModel.networkState.observe(this, Observer {
            if (it.status == Status.FAILED) {
                showErrorPage(it.exception?.message!!)
            } else {
                adapter.setNetworkState(it)
            }
            triggerLoader(it)
        })
    }

    private fun triggerLoader(networkState: NetworkState) {
        github_user_list_swipe_to_refresh.isRefreshing = adapter.itemCount == 0 && networkState.status == Status.RUNNING
    }
}
