package com.example.githubusersearch.repository.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.githubusersearch.api.GithubAPIService
import com.example.githubusersearch.api.response.Item

class GithubDataSourceFactory(private val query: String, private val api: GithubAPIService) : DataSource.Factory<Int, Item>() {

    val pageKeySource = MutableLiveData<GithubPageKeyDataSource>()

    override fun create(): DataSource<Int, Item> {
        val dataSource = GithubPageKeyDataSource(query, api)
        pageKeySource.postValue(dataSource)
        return dataSource
    }

}