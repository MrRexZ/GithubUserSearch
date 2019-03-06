package com.example.githubusersearch.api

import com.example.githubusersearch.api.response.SearchUsersResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Call<SearchUsersResponse>


    companion object {
        private const val BASE_URL = "https://api.github.com/"
        fun create(): GithubAPI {
            val client = OkHttpClient.Builder()
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubAPI::class.java)
        }
    }
}