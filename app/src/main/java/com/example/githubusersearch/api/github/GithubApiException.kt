package com.example.githubusersearch.api.github

import com.example.githubusersearch.api.github.response.GithubErrorResponse

open class GithubApiException(val statusCode: Int, val githubErrorResponse: GithubErrorResponse) : Exception() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GithubApiException

        if (statusCode != other.statusCode) return false
        if (githubErrorResponse != other.githubErrorResponse) return false

        return true
    }

    override fun hashCode(): Int {
        var result = statusCode
        result = 31 * result + githubErrorResponse.hashCode()
        return result
    }
}