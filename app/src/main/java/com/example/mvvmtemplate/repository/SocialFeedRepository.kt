package com.example.mvvmtemplate.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.di.modules.SCHEDULER_IO
import com.example.mvvmtemplate.di.modules.SCHEDULER_MAIN
import com.example.mvvmtemplate.di.modules.SCHEDULER_SINGLE
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SocialFeedRepository @Inject constructor(
    private val dao: SocialFeedsDao,
    private val socialFeedsApiService: SocialFeedApiService,
    @param:Named(SCHEDULER_IO) private val schedulerIO: Scheduler,
    @param:Named(SCHEDULER_SINGLE) private val schedulerSingle: Scheduler,
    @param:Named(SCHEDULER_MAIN) private val schedulerMain: Scheduler
    ) {
    val socialFeedModels: LiveData<List<SocialFeedModel>> = dao.getSocialFeeds()

    val fetchFeedsState: MutableLiveData<FetchState> by lazy {
        MutableLiveData<FetchState>()
    }

    @SuppressLint("CheckResult")
    fun fetchSocialFeeds(forced: Boolean = false) {
        if(fetchFeedsState.value == FetchState.LOADING) return
        Observable.fromCallable { dao.first() == null }
            .subscribeOn(schedulerIO)
            .flatMap {
                if(!it && !forced) {
                    Observable.empty<List<SocialFeedModel>>()//skip
                }else {
                    fetchFeedsState.postValue(FetchState.LOADING)
                    socialFeedsApiService.fetchFeeds()
                }
            }
            .doOnNext{
                dao.insertSocialFeeds(it)
            }
            .observeOn(schedulerMain)
            .subscribe({
                fetchFeedsState.postValue(FetchState.SUCCESS)
            }, {
                fetchFeedsState.postValue(FetchState.FAIL)
                it.printStackTrace()
            })

    }

    fun deleteSocialFeed(socialFeedModel: SocialFeedModel) {
        Observable.fromCallable { dao.deleteSocialFeeds(socialFeedModel) }
            .subscribeOn(schedulerSingle)
            .subscribe()
    }

    fun deleteAllSocialFeeds() {
        Observable.fromCallable { dao.deleteAllSocialFeeds() }
            .subscribeOn(schedulerSingle)
            .subscribe()
    }
}