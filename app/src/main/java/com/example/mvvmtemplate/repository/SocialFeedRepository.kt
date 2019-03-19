package com.example.mvvmtemplate.repository

import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.model.SocialFeedModel
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
    private val socialFeedsDao = socialFeedsDatabase.socialFeedsDao()
    val socialFeedModels get() = socialFeedsDao.getSocialFeeds()

    val fetchFeedsState: MutableLiveData<FetchState> by lazy {
        MutableLiveData<FetchState>()
    }

    fun fetchSocialFeeds(force: Boolean = false) {
        if(fetchFeedsState.value == FetchState.LOADING) return
        appExecutors.diskIO.execute {
            if(force || socialFeedsDao.first() == null) fetchSocialFeeds()
        }
    }

    private fun fetchSocialFeeds(){
        fetchFeedsState.postValue(FetchState.LOADING)
        socialFeedsApiService.fetchFeeds().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                fetchFeedsState.postValue(FetchState.SUCCESS)
                insertSocialFeeds(it)
            }, {
                fetchFeedsState.postValue(FetchState.FAIL)
                it.printStackTrace()
            })
    }

    private fun insertSocialFeeds(socialFeeds: List<SocialFeedModel>) {
        appExecutors.diskIO.execute {
            SocialFeedsLocalDatabase.getInstance().socialFeedsDao().insertSocialFeeds(socialFeeds)
        }
    }

    fun deleteSocialFeed(socialFeedModel: SocialFeedModel) {
        appExecutors.diskIO.execute {
            socialFeedsDao.deleteSocialFeeds(*arrayOf(socialFeedModel))
        }
    }

    fun deleteAllSocialFeeds() {
        appExecutors.diskIO.execute {
            socialFeedsDao.deleteAllSocialFeeds()
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