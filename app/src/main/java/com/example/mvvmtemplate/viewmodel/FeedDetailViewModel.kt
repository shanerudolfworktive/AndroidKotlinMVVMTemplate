package com.example.mvvmtemplate.viewmodel

import android.widget.Toast
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.repository.FeedDetailRepository
import kotlinx.android.synthetic.main.fragment_feed_detail.*

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