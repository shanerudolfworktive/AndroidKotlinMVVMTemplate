package com.example.mvvmtemplate.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SocialFeedRepository constructor(
    private val dao: SocialFeedsDao,
    private val socialFeedsApiService: SocialFeedApiService
) {
    val socialFeedModels: LiveData<List<SocialFeedModel>> = dao.getSocialFeeds()

    val fetchFeedsState: MutableLiveData<FetchState> by lazy {
        MutableLiveData<FetchState>()
    }

    fun fetchSocialFeeds(forced: Boolean = false) {
        if(fetchFeedsState.value == FetchState.LOADING) return
        Observable.fromCallable { dao.first() == null }
            .subscribeOn(Schedulers.io())
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                fetchFeedsState.postValue(FetchState.SUCCESS)
            }, {
                fetchFeedsState.postValue(FetchState.FAIL)
                it.printStackTrace()
            })

    }

    fun deleteSocialFeed(socialFeedModel: SocialFeedModel) {
        Observable.fromCallable { dao.deleteSocialFeeds(socialFeedModel) }
            .subscribeOn(Schedulers.single())
            .subscribe()
    }

    fun deleteAllSocialFeeds() {
        Observable.fromCallable { dao.deleteAllSocialFeeds() }
            .subscribeOn(Schedulers.single())
            .subscribe()
    }
}