package com.example.mvvmtemplate.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.repository.GitHubSearchRepository
import com.example.mvvmtemplate.repository.SocialFeedRepository
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GitHubSearchViewModel constructor(
    private val repository: GitHubSearchRepository = GitHubSearchRepository.getInstance()
): BaseViewModel(){

    val gitHubRepoModels = repository.gitHubRepoModels

    fun gitHubSearch(query: String) {
        repository.gitHubSearch(query);
    }


}