package com.example.mvvmtemplate

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.remote.apiService.SocialFeedApiService
import com.example.view.SocialFeedsAdapter
import com.example.viewmodel.SocialFeedsViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var socialFeedsViewModel: SocialFeedsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSocialFeeds)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val adapter = SocialFeedsAdapter()
        recyclerView.adapter = adapter

        socialFeedsViewModel = ViewModelProviders.of(this).get(SocialFeedsViewModel::class.java)

        socialFeedsViewModel.socialFeeds.observe(this, Observer {
            Log.d("MainActivity LOG", "observed====" + Gson().toJson(it))
            adapter.setSocialFeeds(it)
        })
    }
}
