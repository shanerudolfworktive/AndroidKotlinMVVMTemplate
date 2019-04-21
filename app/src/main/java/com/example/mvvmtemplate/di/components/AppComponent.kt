package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.di.modules.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, DataBaseModule::class, APIModule::class, SchedulersModule::class))
@Singleton
interface AppComponent {
    fun socialFeedViewModelComponent(module: RepoModule): SocialFeedViewModelComponent
    fun feedDetailViewModelComponent(module: RepoModule): FeedDetailViewModelComponent
}
