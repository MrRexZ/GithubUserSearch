package com.example.githubusersearch.repository

import com.example.githubusersearch.api.response.Item
import com.example.githubusersearch.vo.Listing

interface GithubRepository {
    fun searchUsers(query: String): Listing<Item>
}