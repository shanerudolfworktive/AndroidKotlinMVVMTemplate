package com.example.mvvmtemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtemplate.view.SocialFeedsAdapter
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import com.google.gson.Gson

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
