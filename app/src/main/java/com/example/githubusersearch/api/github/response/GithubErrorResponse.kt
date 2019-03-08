package com.example.githubusersearch.api.github.response

import com.google.gson.annotations.SerializedName

class GithubErrorResponse {
    @SerializedName("message")
    var errorMessage: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GithubErrorResponse

        if (errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        return errorMessage?.hashCode() ?: 0
    }


}