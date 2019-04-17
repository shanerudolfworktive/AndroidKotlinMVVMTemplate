package com.example.mvvmtemplate.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao

class FakeSocialFeedsDao : SocialFeedsDao{

    val mutableFeeds = MutableLiveData<HashMap<Long, SocialFeedModel>>().apply {
        value = HashMap()
    }

    val feeds: LiveData<List<SocialFeedModel>> = Transformations.switchMap(mutableFeeds){
        val list = MutableLiveData<List<SocialFeedModel>>()
        list.value = it.values.orEmpty().toList()
        list
    }

    var fakeIDDecrement = -100L
    get() = field--

    override fun getSocialFeeds(): LiveData<List<SocialFeedModel>> {
        return feeds
    }

    override fun insertSocialFeeds(socialFeeds: List<SocialFeedModel>) {
        for(item in socialFeeds) {
            if (item.id == null) item.id = fakeIDDecrement
            mutableFeeds.value?.put(item.id!!, item)
        }
        mutableFeeds.postValue(mutableFeeds.value)
    }

    override fun insertSocialFeed(socialFeed: SocialFeedModel) {
        if (socialFeed.id == null) socialFeed.id = fakeIDDecrement
        mutableFeeds.value?.put(socialFeed.id!!, socialFeed)
        mutableFeeds.postValue(mutableFeeds.value)
    }

    override fun deleteSocialFeed(socialFeed: SocialFeedModel) {
        if(socialFeed.id == null || mutableFeeds.value?.containsKey(socialFeed.id!!)!!) return
        mutableFeeds.value?.remove(socialFeed.id!!)
        mutableFeeds.postValue(mutableFeeds.value)
    }

    override fun deleteSocialFeeds(vararg socialFeeds: SocialFeedModel) {
        for (item in socialFeeds) mutableFeeds.value?.remove(item.id)
        mutableFeeds.postValue(mutableFeeds.value)
    }

    override fun deleteSocialFeeds(socialFeeds: List<SocialFeedModel>) {
        for (item in socialFeeds) mutableFeeds.value?.remove(item.id)
        mutableFeeds.postValue(mutableFeeds.value)
    }

    override fun deleteAllSocialFeeds() {
        mutableFeeds.value?.clear()
        mutableFeeds.postValue(mutableFeeds.value)
    }

    override fun first(): SocialFeedModel? {
        if (feeds.value.isNullOrEmpty()) return null
        return feeds.value?.first()
    }
}