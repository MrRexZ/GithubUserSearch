package com.example.githubusersearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.example.githubusersearch.repository.GithubRepository

class SearchUsersViewModel(private val githubRepository: GithubRepository) : ViewModel() {
    private val query = MutableLiveData<String>()
    private val itemResult = map(query) {
        githubRepository.searchUsers(it)
    }
    val items = switchMap(itemResult) {
        it.pagedList
    }

    val networkState = switchMap(itemResult) {
        it.networkState
    }

    fun showSearchRes(query: String) {
        this.query.value = query
    }

}