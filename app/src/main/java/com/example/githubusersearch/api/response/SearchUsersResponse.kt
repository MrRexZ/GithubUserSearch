package com.example.githubusersearch.api.response

import com.google.gson.annotations.SerializedName

class SearchUsersResponse {
    @SerializedName("total_count")
    var totalCount: Int? = null
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null
    @SerializedName("items")
    var items: List<GithubUserItem>? = null
}

