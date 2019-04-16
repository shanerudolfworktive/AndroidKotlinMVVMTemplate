package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.di.modules.RepoModule
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import dagger.Component

@Component(modules = arrayOf(RepoModule::class))
interface SocialFeedViewModelComponent {
    fun inject(viewmodel: SocialFeedsViewModel)
}