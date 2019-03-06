package com.example.githubusersearch.api.response

import com.google.gson.annotations.SerializedName

class Item {
    @SerializedName("login")
    var login: String? = null
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
}