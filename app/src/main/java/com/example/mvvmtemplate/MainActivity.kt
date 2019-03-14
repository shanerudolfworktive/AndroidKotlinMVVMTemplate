package com.example.mvvmtemplate

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.data.remote.apiService.SocialFeedApiService
import com.example.viewmodel.SocialFeedsViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var socialFeedsViewModel: SocialFeedsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        socialFeedsViewModel = ViewModelProviders.of(this).get(SocialFeedsViewModel::class.java)

        socialFeedsViewModel.socialFeeds.observe(this, Observer {
            Log.d("MainActivity LOG", "observed====" + Gson().toJson(it))
        })

        findViewById<Button>(R.id.fetchFeedsButton).setOnClickListener({
            SocialFeedApiService.create().fetchFeeds().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("MainActivity LOG", Gson().toJson(it))
                },{
                    Log.d("MainActivity LOG", "error")
                    it.printStackTrace();
                })
        });
    }
}
