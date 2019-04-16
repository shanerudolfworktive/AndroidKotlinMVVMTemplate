package com.example.mvvmtemplate.di.modules

import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import com.example.mvvmtemplate.repository.FeedDetailRepository
import com.example.mvvmtemplate.repository.SocialFeedRepository
import dagger.Module
import dagger.Provides

@Module
class RepoModule {
    @Provides
    fun provideSocialFeedsRepo(dao: SocialFeedsDao, api: SocialFeedApiService) : SocialFeedRepository = SocialFeedRepository(dao = dao, socialFeedsApiService = api)

    @Provides
    fun provideFeedDetailRepository(dao: SocialFeedsDao): FeedDetailRepository = FeedDetailRepository(dao)

}