package com.example.mvvmtemplate.di.modules

import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class APIModule {

    fun provideSocialFeedsAPI(service: SocialFeedApiService) = service

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideSocialFeedsAPI() : SocialFeedApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.massrelevance.com")
                .build()
            return retrofit.create(SocialFeedApiService::class.java)
        }
    }

}