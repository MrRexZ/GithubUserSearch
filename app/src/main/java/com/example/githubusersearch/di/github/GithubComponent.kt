package com.example.githubusersearch.di.github

import com.example.githubusersearch.api.github.GithubAPI
import com.example.githubusersearch.api.github.GithubAPIService
import com.example.githubusersearch.api.utils.NetworkAPI
import com.example.githubusersearch.repository.GithubRepository
import com.example.githubusersearch.repository.pagination.InMemoryGithubPageKeyRepository
import com.example.githubusersearch.ui.MainActivity
import com.example.githubusersearch.viewmodel.SearchUsersViewModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class GithubScope

@GithubScope
@Subcomponent(modules = arrayOf(GithubModule::class))
interface GithubComponent {
    fun inject(activity: MainActivity)
    fun searchUsersViewModel(): SearchUsersViewModel
}

@Module
class GithubModule(val activity: MainActivity) {
    @Provides
    @GithubScope
    fun provideGithubAPI(): GithubAPI {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(NetworkAPI.client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubAPI::class.java)
    }

    @Provides
    @GithubScope
    fun provideGithubRepository(githubAPI: GithubAPI): GithubRepository {
        return InMemoryGithubPageKeyRepository(GithubAPIService(githubAPI), Executors.newFixedThreadPool(4))
    }
}