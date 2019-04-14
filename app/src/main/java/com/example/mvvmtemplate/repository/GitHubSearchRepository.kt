package com.example.mvvmtemplate.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.mvvmtemplate.model.GitHubRepoModel
import com.example.mvvmtemplate.model.local.GitHubSearchDatabase
import com.example.mvvmtemplate.model.remote.apiService.GitHubApiService
import com.example.mvvmtemplate.util.AppExecutors

class GitHubSearchRepository private constructor(
    private val appExecutors: AppExecutors = AppExecutors(),
    private val gitHubSearchDatabase: GitHubSearchDatabase = GitHubSearchDatabase.getInstance(),
    private val gitHubApiService: GitHubApiService = GitHubApiService.getInstance()
    ){

    private val queryLiveData = MutableLiveData<String>()

    private val pagingConfig = Config(
        pageSize = 20
    )

    val gitHubRepoModels: LiveData<PagedList<GitHubRepoModel>> = Transformations.switchMap(queryLiveData){
        gitHubSearchDatabase.gitHubSearchDao().reposByName()
            .toLiveData(
                config=pagingConfig,
                boundaryCallback=GitHubSearchBoundaryCallback(it)
            )
    }

    fun gitHubSearch(query: String) {
        appExecutors.diskIO.execute {
            gitHubSearchDatabase.gitHubSearchDao().deleteAllSearches()
            queryLiveData.postValue(query)
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: GitHubSearchRepository? = null
        fun getInstance(): GitHubSearchRepository {
            synchronized(this) {
                if (INSTANCE == null) INSTANCE = GitHubSearchRepository()
            }
            return INSTANCE!!
        }
    }
}