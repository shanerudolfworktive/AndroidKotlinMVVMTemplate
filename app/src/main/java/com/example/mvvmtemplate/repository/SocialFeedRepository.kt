package com.example.mvvmtemplate.repository

import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.model.local.SocialFeedsLocalDatabase
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import com.example.mvvmtemplate.util.AppExecutors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SocialFeedRepository private constructor(
    private val appExecutors: AppExecutors = AppExecutors(),
    private val socialFeedsDatabase: SocialFeedsLocalDatabase = SocialFeedsLocalDatabase.getInstance(),
    private val socialFeedsApiService: SocialFeedApiService = SocialFeedApiService.getInstance()
) {
    private val socialFeedsDao = socialFeedsDatabase.socialFeedsDao();
    var socialFeeds = socialFeedsDao.getSocialFeeds()
    val fetchFeedsState: MutableLiveData<FetchState> by lazy {
        MutableLiveData<FetchState>()
    }

    fun fetchSocialFeeds() {
        appExecutors.diskIO.execute {
            if (socialFeedsDao.first() == null) {
                appExecutors.mainThread.execute {
                    fetchFeedsState.value = FetchState.LOADING
                    socialFeedsApiService.fetchFeeds().observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fetchFeedsState.value = FetchState.SUCCESS
                            AppExecutors().diskIO.execute {
                                SocialFeedsLocalDatabase.getInstance().socialFeedsDao().insertSocialFeeds(it)
                            }
                        }, {
                            fetchFeedsState.value = FetchState.FAIL
                        })
                }
            }
        }
    }


    companion object {
        private var INSTANCE: SocialFeedRepository? = null
        private val lock = Any()
        fun getInstance(): SocialFeedRepository {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = SocialFeedRepository()
                }
            }
            return INSTANCE!!
        }
    }
}