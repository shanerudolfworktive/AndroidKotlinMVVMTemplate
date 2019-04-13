package com.example.mvvmtemplate

import com.example.mvvmtemplate.data.FakeSocialFeedService
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService

class Injection {
    companion object {
        private var SOCIAL_FEED_INSTANCE: FakeSocialFeedService? = null
        private val lock = Any()

        fun provideSocialFeedService(): SocialFeedApiService {
            synchronized(lock) {
                SOCIAL_FEED_INSTANCE = FakeSocialFeedService()
            }
            return SOCIAL_FEED_INSTANCE!!
        }
    }
}