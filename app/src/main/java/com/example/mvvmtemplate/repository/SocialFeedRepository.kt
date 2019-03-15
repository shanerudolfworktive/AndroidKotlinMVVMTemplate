package com.example.mvvmtemplate.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.model.local.SocialFeedsLocalDatabase
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import com.example.mvvmtemplate.util.AppExecutors
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SocialFeedRepository private constructor(
    private val appExecutors: AppExecutors = AppExecutors(),
    private val socialFeedsDatabase: SocialFeedsLocalDatabase = SocialFeedsLocalDatabase.getInstance(),
    private val socialFeedsApiService: SocialFeedApiService = SocialFeedApiService.getInstance()
) {
    private val socialFeedsDao = socialFeedsDatabase.socialFeedsDao();
    var socialFeeds = socialFeedsDao.getSocialFeeds()
    val fetchFeedsState: MutableLiveData<FetchState> by lazy{
        MutableLiveData<FetchState>()
    }

    fun fetchSocialFeeds() {
        Log.e("testing=====", Gson().toJson(socialFeeds.value))
        if(socialFeeds.value?.size?:0 > 0) return

        fetchFeedsState.value = FetchState.LOADING
        socialFeedsApiService.fetchFeeds().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                fetchFeedsState.value = FetchState.SUCCESS
                AppExecutors().diskIO.execute{
                    SocialFeedsLocalDatabase.getInstance().socialFeedsDao().insertSocialFeeds(it)
                }
            }, {
                fetchFeedsState.value = FetchState.FAIL
            })
    }



    companion object {
        private var INSTANCE: SocialFeedRepository? = null
        private val lock = Any()
        fun getInstance(): SocialFeedRepository{
            synchronized(lock) {
                if(INSTANCE == null) {
                    INSTANCE = SocialFeedRepository()
                }
            }
            return INSTANCE!!
        }
    }
}