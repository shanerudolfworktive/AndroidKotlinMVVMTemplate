package com.example.mvvmtemplate.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.repository.FeedDetailRepository

class FeedDetailViewModel constructor(
    private val repository: FeedDetailRepository = FeedDetailRepository.getInstance()
) : BaseViewModel() {
    fun insertSocialFeed(socialFeedModel: SocialFeedModel) {
        repository.insertSocialFeed(socialFeedModel)
    }
}