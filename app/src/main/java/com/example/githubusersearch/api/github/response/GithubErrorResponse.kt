package com.example.githubusersearch.api.github.response

import com.google.gson.annotations.SerializedName

class GithubErrorResponse {
    @SerializedName("message")
    var errorMessage: String? = null
}