package com.example.mvvmtemplate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtemplate.repository.FetchState
import com.example.mvvmtemplate.view.SocialFeedsAdapter
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import com.google.gson.Gson
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

        socialFeedsViewModel.socialfeedModels.observe(this, Observer {
            adapter.submitList(it)
        })

        socialFeedsViewModel.fetchFeedsState.observe(this, Observer {
            Log.e("testing====", Gson().toJson(it))
            socialFeedsRefreshLayout.isRefreshing = it == FetchState.LOADING
        })

        socialFeedsViewModel.fetchSocialFeeds()

        socialFeedsRefreshLayout.setOnRefreshListener {
            socialFeedsViewModel.fetchSocialFeeds(true)
        }

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                socialFeedsViewModel.deleteSocialFeed(adapter.getSocialFeedModelAt(viewHolder.adapterPosition))
            }
        })
            .attachToRecyclerView(recyclerViewSocialFeeds)
    }
}
