package com.example.githubusersearch.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiUtils {
    inline fun <T> request(request: Call<T>,
                                crossinline onSuccess: (T?) -> Unit,
                                crossinline onError: (String) -> Unit) {

        request.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                onError(t.message ?: "Unknown ERROR occurred!")
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    onError("Error Code: ${response.code()}")
                }
            }
        })
    }
}