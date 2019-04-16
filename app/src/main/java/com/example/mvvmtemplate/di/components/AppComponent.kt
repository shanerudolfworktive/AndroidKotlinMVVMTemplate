package com.example.mvvmtemplate.di.components

import android.content.Context
import com.example.mvvmtemplate.di.modules.AppModule
import com.example.mvvmtemplate.di.modules.RepoModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class))
@Singleton
interface AppComponent {
    fun plus(module: RepoModule): SocialFeedViewModelComponent
}
