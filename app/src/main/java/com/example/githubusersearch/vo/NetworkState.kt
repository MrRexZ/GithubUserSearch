package com.example.githubusersearch.vo

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

data class NetworkState constructor(
    val status: Status,
    val exception: Exception? = null) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun ERROR(exception: Exception) = NetworkState(Status.FAILED, exception)
    }
}