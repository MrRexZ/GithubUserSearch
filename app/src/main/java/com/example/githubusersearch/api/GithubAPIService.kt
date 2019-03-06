package com.example.githubusersearch.api

import com.example.githubusersearch.api.response.SearchUsersResponse

class GithubAPIService(private val githubAPI: GithubAPI) {
    fun searchUsers(
        query: String,
        page: Int,
        onSuccess: (SearchUsersResponse?) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val query = githubAPI.searchUsers(query, page)
        ApiUtils.request(query, onSuccess, onFailure)
    }

}