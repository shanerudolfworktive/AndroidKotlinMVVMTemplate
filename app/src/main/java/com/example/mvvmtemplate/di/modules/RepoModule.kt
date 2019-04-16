package com.example.mvvmtemplate.di.modules

import android.content.Context
import com.example.mvvmtemplate.repository.SocialFeedRepository
import dagger.Module
import dagger.Provides

@Module
class RepoModule {

    @Provides
    fun provideSocialFeedsRepo(c: Context) : SocialFeedRepository = SocialFeedRepository(context = c)
}