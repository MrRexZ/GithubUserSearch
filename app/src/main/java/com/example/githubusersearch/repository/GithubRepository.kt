package com.example.githubusersearch.repository

import com.example.githubusersearch.api.github.GithubAPI
import com.example.githubusersearch.api.github.GithubAPIService
import com.example.githubusersearch.api.github.response.GithubUserItem
import com.example.githubusersearch.api.utils.NetworkAPI
import com.example.githubusersearch.repository.pagination.InMemoryGithubPageKeyRepository
import com.example.githubusersearch.vo.Listing
import java.util.concurrent.Executors

interface GithubRepository {
    fun searchUsers(query: String): Listing<GithubUserItem>
}