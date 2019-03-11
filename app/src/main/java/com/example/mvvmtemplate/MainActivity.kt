package com.example.mvvmtemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.data.remote.apiService.SocialFeedApiService
import com.google.gson.Gson
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
