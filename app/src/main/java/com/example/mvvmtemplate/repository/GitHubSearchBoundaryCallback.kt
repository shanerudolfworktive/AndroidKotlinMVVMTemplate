package com.example.mvvmtemplate.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.mvvmtemplate.MainApplication
import com.example.mvvmtemplate.model.GitHubRepoModel
import com.example.mvvmtemplate.model.local.GitHubSearchDao
import com.example.mvvmtemplate.model.local.GitHubSearchDatabase
import com.example.mvvmtemplate.model.remote.apiService.GitHubApiService
import com.example.mvvmtemplate.util.AppExecutors
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GitHubSearchBoundaryCallback(
    private val query: String,
    private val gitHubApiService: GitHubApiService = GitHubApiService.getInstance(),
    private val dao: GitHubSearchDao = GitHubSearchDatabase.getInstance().gitHubSearchDao()
    ): PagedList.BoundaryCallback<GitHubRepoModel>(){
    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

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

        Observable.fromCallable { dao.last()?.pageNumber?: 0 }
            .subscribeOn(Schedulers.io())
            .flatMap {
                gitHubApiService.searchRepos(query, it + 1, NETWORK_PAGE_SIZE)
            }
            .doOnNext {
                dao.insert(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isRequestInProgress = false
            }, {
                isRequestInProgress = false
            })
    }
}