package com.example.mvvmtemplate

import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Injection {
    companion object {
        private var SOCIAL_FEED_INSTANCE: SocialFeedApiService? = null
        private val lock = Any()

        fun provideSocialFeedService(): SocialFeedApiService {
            synchronized(lock) {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://api.massrelevance.com")
                    .build()
                SOCIAL_FEED_INSTANCE = retrofit.create(SocialFeedApiService::class.java)
            }
            return SOCIAL_FEED_INSTANCE!!
        }
    }

}