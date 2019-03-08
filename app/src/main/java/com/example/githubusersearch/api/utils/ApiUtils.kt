package com.example.githubusersearch.api.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

object ApiUtils {
    inline fun <T> request(request: Call<T>,
                           errorHandler: ApiErrorHandler,
                                crossinline onSuccess: (T?) -> Unit,
                                crossinline onError: (Exception) -> Unit) {

        request.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                onError(Exception(t))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    onError(errorHandler.getException(response))
                }
            }
        })
    }
}