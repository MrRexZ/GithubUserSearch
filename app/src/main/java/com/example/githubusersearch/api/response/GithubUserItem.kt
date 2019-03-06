package com.example.githubusersearch.api.response

import com.google.gson.annotations.SerializedName

class GithubUserItem {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("login")
    var login: String? = null
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
}