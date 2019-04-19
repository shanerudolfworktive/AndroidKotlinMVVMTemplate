package com.example.mvvmtemplate.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.MainApplication
import com.example.mvvmtemplate.di.modules.RepoModule
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.repository.FeedDetailRepository
import javax.inject.Inject

class FeedDetailViewModel : BaseViewModel() {

    @Inject lateinit var repository: FeedDetailRepository

    init {
        MainApplication.appComponnet.feedDetailViewModelComponent(RepoModule()).inject(this)
    }

    var name = MutableLiveData<String>().apply{postValue("")}
    var description = MutableLiveData<String>().apply { postValue("") }

    fun insertSocialFeed() {
        val userModel = UserModel(name=name.value, description=description.value)
        repository.insertSocialFeed(SocialFeedModel(user=userModel))
    }
}