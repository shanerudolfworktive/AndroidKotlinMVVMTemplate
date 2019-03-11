package com.example.data.remote.apiService

import com.example.data.model.FeedModel
import retrofit2.http.GET
import io.reactivex.Observable;
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface SocialFeedApiService {

    @GET("/MassRelDemo/kindle.json")
    fun fetchFeeds(): Observable<List<FeedModel>>

    companion object Factory {
        fun create(): SocialFeedApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.massrelevance.com")
                .build()
            return retrofit.create(SocialFeedApiService::class.java)
        }
    }


}