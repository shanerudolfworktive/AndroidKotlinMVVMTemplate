package com.example.mvvmtemplate.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.mvvmtemplate.model.GitHubRepoModel
import com.example.mvvmtemplate.model.local.GitHubSearchDatabase
import com.example.mvvmtemplate.model.remote.apiService.GitHubApiService
import com.example.mvvmtemplate.util.AppExecutors
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GitHubSearchBoundaryCallback(
    private val query: String,
    private val gitHubApiService: GitHubApiService = GitHubApiService.getInstance(),
    private val gitHubSearchDatabase: GitHubSearchDatabase = GitHubSearchDatabase.getInstance()
    ): PagedList.BoundaryCallback<GitHubRepoModel>(){
    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d("RepoBoundaryCallback", "onZeroItemsLoaded")

        requestAndSaveData(query)
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: GitHubRepoModel) {
        Log.d("RepoBoundaryCallback", "onItemAtEndLoaded")
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        gitHubApiService.searchRepos(query, lastRequestedPage, NETWORK_PAGE_SIZE).subscribeOn(Schedulers.io())
            .flatMap {
                gitHubSearchDatabase.gitHubSearchDao().insert(it)
                Observable.just(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                lastRequestedPage++
                isRequestInProgress = false
            }, {
                isRequestInProgress = false
                it.printStackTrace()
            })
    }
}