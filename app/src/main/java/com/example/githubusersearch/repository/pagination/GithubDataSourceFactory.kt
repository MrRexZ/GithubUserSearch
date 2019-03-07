package com.example.githubusersearch.repository.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.githubusersearch.api.github.GithubAPIService
import com.example.githubusersearch.api.github.response.GithubUserItem

class GithubDataSourceFactory(private val query: String, private val api: GithubAPIService) : DataSource.Factory<Int, GithubUserItem>() {

    val pageKeySource = MutableLiveData<GithubPageKeyDataSource>()

    override fun create(): DataSource<Int, GithubUserItem> {
        val dataSource = GithubPageKeyDataSource(query, api)
        pageKeySource.postValue(dataSource)
        return dataSource
    }

}