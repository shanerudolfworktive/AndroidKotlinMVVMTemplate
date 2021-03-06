package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.di.modules.RepoModule
import com.example.mvvmtemplate.viewmodel.FeedDetailViewModel
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(RepoModule::class))
interface FeedDetailViewModelComponent {
    fun inject(viewmodel: FeedDetailViewModel)
}