package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.repository.SocialFeedRepository

class SocialFeedsViewModel constructor(
    val repository: SocialFeedRepository = SocialFeedRepository.getInstance()
): ViewModel(){
    var socialFeeds = repository.socialFeeds

}