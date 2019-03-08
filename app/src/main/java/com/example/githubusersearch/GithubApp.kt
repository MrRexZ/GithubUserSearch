package com.example.githubusersearch

import android.app.Application
import com.example.githubusersearch.di.AppComponent
import com.example.githubusersearch.di.AppModule
import com.example.githubusersearch.di.DaggerAppComponent

class GithubApp: Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}