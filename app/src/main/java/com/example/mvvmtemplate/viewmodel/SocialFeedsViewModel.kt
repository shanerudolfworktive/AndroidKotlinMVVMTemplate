package com.example.mvvmtemplate.viewmodel

import com.example.mvvmtemplate.MainApplication
import com.example.mvvmtemplate.di.modules.RepoModule
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.repository.SocialFeedRepository
import javax.inject.Inject

class SocialFeedsViewModel : BaseViewModel() {
    @Inject lateinit var repository: SocialFeedRepository

    init {
        MainApplication.appComponnet.socialFeedViewModelComponent(RepoModule()).inject(this)
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