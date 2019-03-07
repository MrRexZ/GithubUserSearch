package com.example.githubusersearch.vo

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val pagedListHasData: LiveData<Boolean>)
