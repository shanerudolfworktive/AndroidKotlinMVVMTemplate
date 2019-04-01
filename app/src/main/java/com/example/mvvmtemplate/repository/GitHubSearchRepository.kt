package com.example.mvvmtemplate.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.mvvmtemplate.model.GitHubRepoModel
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.local.GitHubSearchDatabase
import com.example.mvvmtemplate.model.remote.apiService.GitHubApiService
import com.example.mvvmtemplate.util.AppExecutors
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GitHubSearchRepository private constructor(
    private val appExecutors: AppExecutors = AppExecutors(),
    private val gitHubSearchDatabase: GitHubSearchDatabase = GitHubSearchDatabase.getInstance(),
    private val gitHubApiService: GitHubApiService = GitHubApiService.getInstance()
    ){

    private val queryLiveData = MutableLiveData<String>()

    private val pagingConfig = Config(
        pageSize = 10,
        prefetchDistance = 100,
        enablePlaceholders = false
    )

    val gitHubRepoModels: LiveData<PagedList<GitHubRepoModel>> = Transformations.switchMap(queryLiveData){
        gitHubSearchDatabase.gitHubSearchDao().reposByName()
            .toLiveData(
                config=pagingConfig,
                boundaryCallback=GitHubSearchBoundaryCallback(it)
            )
    }

    fun gitHubSearch(query: String) {
        Log.d("GithubRepository", "New query: $query")
        queryLiveData.postValue(query)
    }

    companion object {
        private var INSTANCE: GitHubSearchRepository? = null
        fun getInstance(): GitHubSearchRepository {
            synchronized(this) {
                if (INSTANCE == null) INSTANCE = GitHubSearchRepository()
            }
            return INSTANCE!!
        }
    }
}