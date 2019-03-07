package com.example.githubusersearch.api.github

import com.example.githubusersearch.api.github.response.GithubErrorResponse

class GithubApiException(val githubErrorResponse: GithubErrorResponse) : Exception()