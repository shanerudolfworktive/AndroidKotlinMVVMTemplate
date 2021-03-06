package com.example.mvvmtemplate.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.repository.FeedDetailRepository
import javax.inject.Inject

class FeedDetailViewModel @Inject constructor(private val repository: FeedDetailRepository): BaseViewModel() {

//    @Inject lateinit var repository: FeedDetailRepository

    init {
//        MainApplication.appComponnet.feedDetailViewModelComponent(RepoModule()).inject(this)
    }

    var name = MutableLiveData<String>()
    var description = MutableLiveData<String>()

    fun insertSocialFeed() {
        val userModel = UserModel(name=name.value, description=description.value)
        repository.insertSocialFeed(SocialFeedModel(user=userModel))
    }
}