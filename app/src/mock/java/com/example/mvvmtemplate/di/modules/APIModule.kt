package com.example.mvvmtemplate.di.modules

import com.example.mvvmtemplate.data.FakeSocialFeedService
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class APIModule {

    @Provides
    @Singleton
    fun provideSocialFeedsAPI() : SocialFeedApiService = FakeSocialFeedService().apply {
        add(SocialFeedModel("fake_created_at", -1, UserModel(-1, "fake_name", "fake_description")))
        add(SocialFeedModel("fake_created_at", -2, UserModel(-2, "fake_name", "fake_description")))
        add(SocialFeedModel("fake_created_at", -3, UserModel(-3, "fake_name", "fake_description")))
    }
}