package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.di.modules.RepoModule
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(RepoModule::class))
interface SocialFeedViewModelComponent {
    fun inject(viewmodel: SocialFeedsViewModel)
}