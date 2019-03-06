package com.example.githubusersearch.repository.pagination

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.example.githubusersearch.api.GithubAPI
import com.example.githubusersearch.api.GithubAPIService
import com.example.githubusersearch.api.response.GithubUserItem
import com.example.githubusersearch.repository.GithubRepository
import com.example.githubusersearch.vo.Listing
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class InMemoryGithubPageKeyRepository(private val githubAPI: GithubAPIService, private val networkExecutor: Executor) :
    GithubRepository {
    override fun searchUsers(query: String): Listing<GithubUserItem> {
        val sourceFactory = GithubDataSourceFactory(query, githubAPI)
        val pagedListLiveData = sourceFactory.toLiveData(20, fetchExecutor = networkExecutor)
        return Listing(pagedListLiveData, networkState = Transformations.switchMap(sourceFactory.pageKeySource) {it.network})
    }


}