package com.example.mvvmtemplate.repository

import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.model.local.SocialFeedsLocalDatabase
import com.example.mvvmtemplate.util.AppExecutors
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedDetailRepository @Inject constructor(
    private val dao: SocialFeedsDao
    ) {
    fun insertSocialFeed(socialFeedModel: SocialFeedModel) {
        Observable.fromCallable { dao.insertSocialFeed(socialFeedModel) }
            .subscribeOn(Schedulers.single())
            .subscribe()
    }
}