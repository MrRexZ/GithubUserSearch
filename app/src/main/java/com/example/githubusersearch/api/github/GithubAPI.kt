package com.example.githubusersearch.api.github

import com.example.githubusersearch.api.github.response.SearchUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Call<SearchUsersResponse>
}