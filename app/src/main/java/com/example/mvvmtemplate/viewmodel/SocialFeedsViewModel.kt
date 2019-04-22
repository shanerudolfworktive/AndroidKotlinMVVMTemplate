package com.example.mvvmtemplate.viewmodel

import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.repository.SocialFeedRepository
import javax.inject.Inject

class SocialFeedsViewModel @Inject constructor(private val repository: SocialFeedRepository): BaseViewModel() {
//    @Inject lateinit var repository: SocialFeedRepository

    init {
//        MainApplication.appComponnet.socialFeedViewModelComponent(RepoModule()).inject(this)
    }

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