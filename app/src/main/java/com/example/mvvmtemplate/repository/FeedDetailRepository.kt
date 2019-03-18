package com.example.mvvmtemplate.repository

import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.local.SocialFeedsLocalDatabase
import com.example.mvvmtemplate.util.AppExecutors

class FeedDetailRepository private constructor(
    private val appExecutors: AppExecutors = AppExecutors(),
    private val socialFeedsDatabase: SocialFeedsLocalDatabase = SocialFeedsLocalDatabase.getInstance()
) {
    private val socialFeedsDao = socialFeedsDatabase.socialFeedsDao()
    fun insertSocialFeed(socialFeedModel: SocialFeedModel) {
        appExecutors.diskIO.execute {
            socialFeedsDao.insertSocialFeed(socialFeedModel)
        }
    }

    companion object {
        private var INSTANCE: FeedDetailRepository? = null
        fun getInstance(): FeedDetailRepository {
            synchronized(this) {
                if (INSTANCE == null) INSTANCE = FeedDetailRepository()
            }
            return INSTANCE!!
        }
    }

}