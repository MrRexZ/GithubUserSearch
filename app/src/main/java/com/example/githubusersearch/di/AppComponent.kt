package com.example.githubusersearch.di

import com.example.githubusersearch.GithubApp
import com.example.githubusersearch.di.github.GithubComponent
import com.example.githubusersearch.di.github.GithubModule
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: GithubApp)
    fun plus(githubModule: GithubModule): GithubComponent
}

@Module
class AppModule(val app: GithubApp) {
    @Provides
    @Singleton
    fun provideApp() = app

}