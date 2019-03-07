package com.example.githubusersearch.api.github

import com.example.githubusersearch.api.github.response.SearchUsersResponse
import com.example.githubusersearch.api.utils.ApiUtils
import kotlin.Exception

class GithubAPIService(private val githubAPI: GithubAPI) {
    private val githubApiErrorHandler = GithubApiErrorHandler()
    fun searchUsers(
        query: String,
        page: Int,
        onSuccess: (SearchUsersResponse?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val searchQuery = githubAPI.searchUsers(query, page)
        ApiUtils.request(searchQuery, githubApiErrorHandler, onSuccess, onFailure)
    }

}