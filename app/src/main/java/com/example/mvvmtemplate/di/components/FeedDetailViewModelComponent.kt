package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.di.modules.RepoModule
import com.example.mvvmtemplate.viewmodel.FeedDetailViewModel
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(modules = arrayOf(RepoModule::class))
interface FeedDetailViewModelComponent {
    fun inject(viewmodel: FeedDetailViewModel)
}