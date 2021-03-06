package com.example.githubusersearch.repository.pagination

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.githubusersearch.api.github.GithubAPIService
import com.example.githubusersearch.api.github.response.GithubUserItem
import com.example.githubusersearch.repository.GithubRepository
import com.example.githubusersearch.vo.Listing
import java.util.concurrent.Executor


class InMemoryGithubPageKeyRepository(private val githubAPI: GithubAPIService, private val networkExecutor: Executor) :
    GithubRepository {
    override fun searchUsers(query: String): Listing<GithubUserItem> {
        val sourceFactory = GithubDataSourceFactory(query, githubAPI)
        val pagedListHasData = MutableLiveData<Boolean>()
        pagedListHasData.value =true
        val pagedListLiveData = LivePagedListBuilder<Int, GithubUserItem>(sourceFactory, 20).setBoundaryCallback(object : PagedList.BoundaryCallback<GithubUserItem>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                pagedListHasData.postValue(false)
            }
        }).build()

        //val pagedListLiveData = sourceFactory.toLiveData(20, fetchExecutor = networkExecutor)

        return Listing(pagedListLiveData, networkState = Transformations.switchMap(sourceFactory.pageKeySource) {it.network}, pagedListHasData = pagedListHasData)
    }


}