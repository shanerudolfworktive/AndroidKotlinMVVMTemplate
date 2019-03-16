package com.example.mvvmtemplate.model.remote.apiService

import com.example.mvvmtemplate.model.SocialFeedModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SocialFeedApiService {

    @GET("/MassRelDemo/kindle.json")
    fun fetchFeeds(): Observable<List<SocialFeedModel>>

    companion object {
        private var INSTANCE: SocialFeedApiService? = null
        private val lock = Any()

        fun getInstance(): SocialFeedApiService {
            synchronized(lock) {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://api.massrelevance.com")
                    .build()
                INSTANCE = retrofit.create(SocialFeedApiService::class.java)
            }
            return INSTANCE!!
        }
    }


}