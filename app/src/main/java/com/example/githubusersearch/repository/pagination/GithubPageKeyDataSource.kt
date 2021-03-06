package com.example.githubusersearch.repository.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.githubusersearch.api.github.GithubAPIService
import com.example.githubusersearch.api.github.response.GithubUserItem
import com.example.githubusersearch.vo.NetworkState

//TODO: Implement retry mechanism on network error
class GithubPageKeyDataSource(
    private val query: String,
    private val api: GithubAPIService
) : PageKeyedDataSource<Int, GithubUserItem>() {

    val network = MutableLiveData<NetworkState>()

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUserItem>) {
        //Not implemented because there is no prepend functionality
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GithubUserItem>) {
        val currentPage = 1
        val nextPage = currentPage + 1
        postNetworkState(NetworkState.LOADING)
        api.searchUsers(query, currentPage, onSuccess = { response ->
            val items = response?.items ?: emptyList()
            callback.onResult(items, currentPage, nextPage)
            postNetworkState(NetworkState.LOADED)
        }, onFailure = {
            //
            postNetworkState(NetworkState.error(it))
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUserItem>) {
        val currentPage = params.key
        val nextPage = currentPage + 1
        postNetworkState(NetworkState.LOADING)
        api.searchUsers(query, currentPage, onSuccess = { response ->
            val items = response?.items ?: emptyList()
            callback.onResult(items, nextPage)
            postNetworkState(NetworkState.LOADED)
        }, onFailure = {
            //TODO: Implement failure handling
            postNetworkState(NetworkState.error(it))
        })
    }

    private fun postNetworkState(state: NetworkState) {
        network.postValue(state)
    }
}