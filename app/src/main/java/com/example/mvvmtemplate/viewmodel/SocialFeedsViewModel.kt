package com.example.mvvmtemplate.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvmtemplate.model.local.SocialFeedsLocalDatabase
import com.example.mvvmtemplate.repository.FetchState
import com.example.mvvmtemplate.repository.SocialFeedRepository
import com.example.mvvmtemplate.util.AppExecutors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SocialFeedsViewModel constructor(
    val repository: SocialFeedRepository = SocialFeedRepository.getInstance()
): ViewModel(){
    var socialFeeds = repository.socialFeeds
    var fetchFeedsState = repository.fetchFeedsState

    fun fetchSocialFeeds() {
        repository.fetchSocialFeeds()
    }
}