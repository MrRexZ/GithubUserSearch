package com.example.githubusersearch.utils

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import androidx.test.espresso.IdlingResource
import com.example.githubusersearch.api.github.GithubAPI
import com.example.githubusersearch.api.utils.NetworkAPI
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class IdlingResourceRule : TestRule {

    private val resource: IdlingResource

    init {
        resource = OkHttp3IdlingResource.create("OkHttp", NetworkAPI.client)
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                IdlingRegistry.getInstance().register(resource)
                base.evaluate()
                IdlingRegistry.getInstance().unregister(resource)
            }
        }
    }
}