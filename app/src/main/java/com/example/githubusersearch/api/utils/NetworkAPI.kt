package com.example.githubusersearch.api.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object NetworkAPI {
    val client: OkHttpClient = createOkHttpClient()

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

}