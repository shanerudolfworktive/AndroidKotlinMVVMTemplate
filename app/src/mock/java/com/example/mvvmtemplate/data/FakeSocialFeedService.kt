package com.example.mvvmtemplate.data

import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import io.reactivex.Observable

class FakeSocialFeedService : SocialFeedApiService{
    override fun fetchFeeds(): Observable<List<SocialFeedModel>> {
        return Observable.just(data)
    }

    fun clear() {
        data.clear()
    }

    fun add(socialFeed: SocialFeedModel) {
       data.add(socialFeed)
    }

    companion object {
        private val data = mutableListOf<SocialFeedModel>()
    }

}