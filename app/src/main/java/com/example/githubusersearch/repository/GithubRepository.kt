package com.example.githubusersearch.repository

import com.example.githubusersearch.api.GithubAPI
import com.example.githubusersearch.api.GithubAPIService
import com.example.githubusersearch.api.response.GithubUserItem
import com.example.githubusersearch.repository.pagination.InMemoryGithubPageKeyRepository
import com.example.githubusersearch.vo.Listing
import java.util.concurrent.Executors

interface GithubRepository {
    fun searchUsers(query: String): Listing<GithubUserItem>

    companion object {
        fun create(): InMemoryGithubPageKeyRepository {
            return InMemoryGithubPageKeyRepository(GithubAPIService(GithubAPI.create()), Executors.newFixedThreadPool(4))
        }
    }
}