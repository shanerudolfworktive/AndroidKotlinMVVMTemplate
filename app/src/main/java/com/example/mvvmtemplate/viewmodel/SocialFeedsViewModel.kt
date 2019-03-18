package com.example.mvvmtemplate.viewmodel

import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.repository.SocialFeedRepository

class SocialFeedsViewModel constructor(
    private val repository: SocialFeedRepository = SocialFeedRepository.getInstance()
    ) : BaseViewModel() {
    var fetchFeedsState = repository.fetchFeedsState
    var socialfeedModels = repository.socialFeedModels

    fun fetchSocialFeeds(force: Boolean = false) {
        repository.fetchSocialFeeds(force)
    }

    fun deleteSocialFeed(socialFeedModel: SocialFeedModel) {
        repository.deleteSocialFeed(socialFeedModel)
    }

    fun deleteAllSocialFeeds() {
        repository.deleteAllSocialFeeds()
    }
}