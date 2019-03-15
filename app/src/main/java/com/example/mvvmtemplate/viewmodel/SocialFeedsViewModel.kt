package com.example.mvvmtemplate.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvmtemplate.model.local.SocialFeedsLocalDatabase
import com.example.mvvmtemplate.repository.SocialFeedRepository
import com.example.mvvmtemplate.util.AppExecutors

class SocialFeedsViewModel constructor(
    private val appExecutors: AppExecutors = AppExecutors(),
    private val repository: SocialFeedRepository = SocialFeedRepository.getInstance(),
    private val socialFeedsDatabase: SocialFeedsLocalDatabase = SocialFeedsLocalDatabase.getInstance()
) : ViewModel() {
    private val socialFeedsDao = socialFeedsDatabase.socialFeedsDao()
    var socialFeeds = repository.socialFeeds
    var fetchFeedsState = repository.fetchFeedsState

    fun fetchSocialFeeds(force: Boolean = false) {
        if(force) {
            repository.fetchSocialFeeds()
        }else {
            appExecutors.diskIO.execute {
                if (socialFeedsDao.first() == null) {
                    repository.fetchSocialFeeds()
                }
            }
        }
    }
}