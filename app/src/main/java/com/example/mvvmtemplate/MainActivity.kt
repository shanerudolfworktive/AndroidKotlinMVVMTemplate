package com.example.mvvmtemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmtemplate.repository.FetchState
import com.example.mvvmtemplate.view.SocialFeedsAdapter
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var socialFeedsViewModel: SocialFeedsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewSocialFeeds.layoutManager = LinearLayoutManager(this)
        recyclerViewSocialFeeds.setHasFixedSize(true)

        val adapter = SocialFeedsAdapter()
        recyclerViewSocialFeeds.adapter = adapter

        socialFeedsViewModel = ViewModelProviders.of(this).get(SocialFeedsViewModel::class.java)

        socialFeedsViewModel.socialFeeds.observe(this, Observer {
            adapter.submitList(it)
        })

        socialFeedsViewModel.fetchFeedsState.observe(this, Observer {
            socialFeedsRefreshLayout.isRefreshing = it == FetchState.LOADING
        })

        socialFeedsViewModel.fetchSocialFeeds()

        socialFeedsRefreshLayout.setOnRefreshListener {
            socialFeedsViewModel.fetchSocialFeeds(true)
        }
    }
}
