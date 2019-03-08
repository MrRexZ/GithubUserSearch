package com.example.githubusersearch.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.githubusersearch.api.github.response.GithubUserItem
import com.example.githubusersearch.repository.GithubRepository
import com.example.githubusersearch.vo.NetworkState
import javax.inject.Inject


class SearchUsersViewModel @Inject constructor(val githubRepository: GithubRepository) : ViewModel() {
    private val query = MutableLiveData<String>()
    private val itemResult = map(query) {
        githubRepository.searchUsers(it)
    }
    val items: LiveData<PagedList<GithubUserItem>> = switchMap(itemResult) {
        it.pagedList
    }

    val networkState: LiveData<NetworkState> = switchMap(itemResult) {
        it.networkState
    }

    val showEmptyUserList: LiveData<Boolean> = switchMap(itemResult) {
        map(it.pagedListHasData) { hasData ->
            !hasData
        }
    }

    fun showSearchRes(query: String) {
        this.query.postValue(query)
    }

    fun retrySearch() {
        this.query.postValue(query.value)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getSearchQuery(): LiveData<String> {
        return query
    }

}