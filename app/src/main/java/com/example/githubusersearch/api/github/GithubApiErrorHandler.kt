package com.example.githubusersearch.api.github

import com.example.githubusersearch.api.github.response.GithubErrorResponse
import com.example.githubusersearch.api.utils.ApiErrorHandler
import com.google.gson.Gson
import retrofit2.Response
import kotlin.Exception

class GithubApiErrorHandler : ApiErrorHandler {
    private val gson: Gson = Gson()
    override fun getException(response: Response<*>): Exception {
        val githubErr = gson.fromJson(response.errorBody()?.charStream(), GithubErrorResponse::class.java)
        return GithubApiException(response.code(), githubErr)
    }

}