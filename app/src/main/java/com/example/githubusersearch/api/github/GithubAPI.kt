package com.example.githubusersearch.api.github

import com.example.githubusersearch.api.github.response.SearchUsersResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        val client: OkHttpClient = createOkHttpClient()

        private fun createOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        fun create(): GithubAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubAPI::class.java)
        }
    }
}