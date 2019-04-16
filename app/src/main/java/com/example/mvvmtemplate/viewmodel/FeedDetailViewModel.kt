package com.example.mvvmtemplate.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.repository.FeedDetailRepository

class FeedDetailViewModel constructor(
    private val repository: FeedDetailRepository = FeedDetailRepository.getInstance()
) : BaseViewModel() {

    var name = MutableLiveData<String>().apply{postValue("")}
    var description = MutableLiveData<String>().apply { postValue("") }

    fun insertSocialFeed() {
        val userModel = UserModel(name=name.value, description=description.value)
        repository.insertSocialFeed(SocialFeedModel(user=userModel))
    }
}