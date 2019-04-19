package com.example.mvvmtemplate.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao

class FakeSocialFeedsDao : SocialFeedsDao{

    val mutableFeeds = HashMap<Long, SocialFeedModel>()

    val feeds: MutableLiveData<List<SocialFeedModel>> = MutableLiveData()

    var fakeIDDecrement = -100L
    get() = field--

    override fun getSocialFeeds(): LiveData<List<SocialFeedModel>> {
        return feeds
    }

    override fun insertSocialFeeds(socialFeeds: List<SocialFeedModel>) {
        for(item in socialFeeds) {
            if (item.id == null) item.id = fakeIDDecrement
            mutableFeeds.put(item.id!!, item)
        }
        feeds.postValue(mutableFeeds.values.toList())
    }

    override fun insertSocialFeed(socialFeed: SocialFeedModel) {
        if (socialFeed.id == null) socialFeed.id = fakeIDDecrement
        mutableFeeds.put(socialFeed.id!!, socialFeed)
        feeds.postValue(mutableFeeds.values.toList())
    }

    override fun deleteSocialFeed(socialFeed: SocialFeedModel) {
        if(socialFeed.id == null || mutableFeeds.containsKey(socialFeed.id!!)) return
        mutableFeeds.remove(socialFeed.id!!)
        feeds.postValue(mutableFeeds.values.toList())
    }

    override fun deleteSocialFeeds(vararg socialFeeds: SocialFeedModel) {
        for (item in socialFeeds) mutableFeeds.remove(item.id)
        feeds.postValue(mutableFeeds.values.toList())
    }

    override fun deleteSocialFeeds(socialFeeds: List<SocialFeedModel>) {
        for (item in socialFeeds) mutableFeeds.remove(item.id)
        feeds.postValue(mutableFeeds.values.toList())
    }

    override fun deleteAllSocialFeeds() {
        mutableFeeds.clear()
        feeds.postValue(mutableFeeds.values.toList())
    }

    override fun first(): SocialFeedModel? {
        if (feeds.value.isNullOrEmpty()) return null
        return feeds.value?.first()
    }
}