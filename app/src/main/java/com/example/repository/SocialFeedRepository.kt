package com.example.repository

import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.data.local.SocialFeedsDao
import com.example.data.local.SocialFeedsLocalDatabase
import com.example.data.model.SocialFeedModel
import com.example.mvvmtemplate.MainApplication
import com.example.util.AppExecutors

class SocialFeedRepository private constructor(
    private val appExecutors: AppExecutors = AppExecutors(),
    private val socialFeedsDatabase: SocialFeedsLocalDatabase = SocialFeedsLocalDatabase.getInstance()
) {
    private val socialFeedsDao = socialFeedsDatabase.socialFeedsDao();
    public var socialFeeds = socialFeedsDao.getSocialFeeds()

    fun fetchSocialFeeds() {
        appExecutors.diskIO.execute {
            val socialFeeds = socialFeedsDao.getSocialFeeds()
            appExecutors.mainThread.execute{
                this.socialFeeds = socialFeeds
            }
        }
    }

    companion object {
        private var INSTANCE: SocialFeedRepository? = null
        private val lock = Any()
        fun getInstance(): SocialFeedRepository{
            synchronized(lock) {
                if(INSTANCE == null) {
                    INSTANCE = SocialFeedRepository()
                }
            }
            return INSTANCE!!
        }
    }
}