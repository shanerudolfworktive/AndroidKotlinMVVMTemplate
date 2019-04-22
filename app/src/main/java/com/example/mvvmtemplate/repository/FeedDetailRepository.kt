package com.example.mvvmtemplate.repository

import com.example.mvvmtemplate.di.modules.SCHEDULER_SINGLE
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class FeedDetailRepository @Inject constructor(
    private val dao: SocialFeedsDao,
    @param:Named(SCHEDULER_SINGLE) private val schedulerSingle: Scheduler
    ) {
    fun insertSocialFeed(socialFeedModel: SocialFeedModel?) {
        Observable.fromCallable { dao.insertSocialFeed(socialFeedModel!!) }
            .subscribeOn(schedulerSingle)
            .subscribe()
    }
}