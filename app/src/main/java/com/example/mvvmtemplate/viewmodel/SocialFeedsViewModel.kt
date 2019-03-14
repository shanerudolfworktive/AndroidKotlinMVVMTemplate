package com.example.mvvmtemplate.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvmtemplate.repository.SocialFeedRepository

class SocialFeedsViewModel constructor(
    val repository: SocialFeedRepository = SocialFeedRepository.getInstance()
): ViewModel(){
    var socialFeeds = repository.socialFeeds

}