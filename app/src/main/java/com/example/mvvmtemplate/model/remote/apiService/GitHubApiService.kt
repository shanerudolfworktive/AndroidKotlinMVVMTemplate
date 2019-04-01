package com.example.mvvmtemplate.model.remote.apiService

import com.example.mvvmtemplate.model.GitHubSearchResponseModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

    @GET("search/repositories?sort=stars")
    fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Observable<GitHubSearchResponseModel>

    companion object {
        private var INSTANCE: GitHubApiService? = null
        private val lock = Any()

        fun getInstance(): GitHubApiService {
            synchronized(lock) {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()
                INSTANCE = retrofit.create(GitHubApiService::class.java)
            }
            return INSTANCE!!
        }
    }
}