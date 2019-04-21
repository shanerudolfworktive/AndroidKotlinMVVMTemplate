package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.FeedDetailViewModelUnitTest
import com.example.mvvmtemplate.SocialFeedsViewModelUnitTest
import com.example.mvvmtemplate.di.modules.RepoModule
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(RepoModule::class))
interface UnitTestComponent{
    fun inject(test: FeedDetailViewModelUnitTest)
    fun inject(test: SocialFeedsViewModelUnitTest)
}