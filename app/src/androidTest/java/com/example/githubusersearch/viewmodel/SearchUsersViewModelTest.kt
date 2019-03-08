package com.example.githubusersearch.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.githubusersearch.api.github.GithubApiException
import com.example.githubusersearch.api.github.response.GithubErrorResponse
import com.example.githubusersearch.api.github.response.GithubUserItem
import com.example.githubusersearch.api.utils.StatusCode
import com.example.githubusersearch.repository.GithubRepository
import com.example.githubusersearch.vo.Listing
import com.example.githubusersearch.vo.NetworkState
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import com.nhaarman.mockito_kotlin.*
import kotlin.Exception

class SearchUsersViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRole = InstantTaskExecutorRule()
    private val networkState: Observer<NetworkState> = mock()
    private val showEmptyUserList: Observer<Boolean> = mock()
    private val githubRepository: GithubRepository = mock()
    private val queryVal: Observer<String> = mock()
    private val viewModel = SearchUsersViewModel(githubRepository)

    @Before
    fun setUp() {
        viewModel.networkState.observeForever(networkState)
        viewModel.showEmptyUserList.observeForever(showEmptyUserList)
        viewModel.getSearchQuery().observeForever(queryVal)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun validSearchQueryResultsSuccess() {
        stubSearchQuerySuccessfull()
        viewModel.showSearchRes("hello")
        verify(networkState).onChanged(NetworkState.LOADED)
        verify(showEmptyUserList).onChanged(false)
    }


    @Test
    fun invalidSearchQueryResultsFailure() {
        val expectedException = GithubApiException(StatusCode.UNPROCESSABLE_ENTITY, GithubErrorResponse())
        stubSearchQueryUnsuccessfull(expectedException)
        viewModel.showSearchRes("\\")
        verify(networkState).onChanged(
            NetworkState.ERROR(expectedException)
        )
    }

    @Test
    fun retrySearchUsesSameQuery() {
        stubSearchQuerySuccessfull()
        viewModel.showSearchRes("hey")
        viewModel.retrySearch()
        verify(queryVal, times(2)).onChanged("hey")
    }

    private fun stubSearchQuerySuccessfull() {
        val networkStateMock: MutableLiveData<NetworkState> = MutableLiveData()
        val pageListHasDataMock: MutableLiveData<Boolean> = MutableLiveData()
        whenever(githubRepository.searchUsers(any())).then {
            networkStateMock.value = NetworkState.LOADED
            pageListHasDataMock.value = true
            Listing<GithubUserItem>(mock(), networkStateMock, pageListHasDataMock)
        }
    }

    private fun stubSearchQueryUnsuccessfull(expectedException: Exception) {
        val networkStateMock: MutableLiveData<NetworkState> = MutableLiveData()
        val pageListHasDataMock: MutableLiveData<Boolean> = MutableLiveData()
        whenever(githubRepository.searchUsers("\\")).then {
            networkStateMock.value = NetworkState.ERROR(expectedException)
            Listing<GithubUserItem>(mock(), networkStateMock, pageListHasDataMock)
        }
    }

}