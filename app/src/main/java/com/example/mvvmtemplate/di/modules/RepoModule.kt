package com.example.mvvmtemplate.di.modules

import com.example.mvvmtemplate.repository.SocialFeedRepository
import dagger.Module
import dagger.Provides

@Module
class RepoModule {

    @Provides
    fun provideSocialFeedsRepo() : SocialFeedRepository = SocialFeedRepository.getInstance()
}