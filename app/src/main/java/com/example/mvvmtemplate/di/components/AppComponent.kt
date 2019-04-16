package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.di.modules.APIModule
import com.example.mvvmtemplate.di.modules.AppModule
import com.example.mvvmtemplate.di.modules.DataBaseModule
import com.example.mvvmtemplate.di.modules.RepoModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, DataBaseModule::class, APIModule::class))
@Singleton
interface AppComponent {
    fun plus(module: RepoModule): SocialFeedViewModelComponent
    fun plusAnything(module: RepoModule): FeedDetailViewModelComponent
}
