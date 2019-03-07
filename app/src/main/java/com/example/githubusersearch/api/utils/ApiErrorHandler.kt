package com.example.githubusersearch.api.utils

import retrofit2.Response

interface ApiErrorHandler {
    abstract fun getException(response: Response<*>): Exception
}