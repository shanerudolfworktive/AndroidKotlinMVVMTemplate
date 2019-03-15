package com.example.mvvmtemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtemplate.model.local.SocialFeedsLocalDatabase
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import com.example.mvvmtemplate.util.AppExecutors
import com.example.mvvmtemplate.view.SocialFeedsAdapter
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
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
            adapter.submitList(it)
        })

        socialFeedsViewModel.fetchFeedsState.observe(this, Observer {
            Log.d("MainActivity LOG", "State====" + Gson().toJson(it))
        })

        socialFeedsViewModel.fetchSocialFeeds()

//        SocialFeedApiService.getInstance().fetchFeeds().observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                Log.d("MainActivity LOG", Gson().toJson(it))
//                AppExecutors().diskIO.execute{
//                    SocialFeedsLocalDatabase.getInstance().socialFeedsDao().insertSocialFeeds(it)
//                }
//            },{
//                Log.d("MainActivity LOG", "error")
//                it.printStackTrace();
//            })

    }
}
