package com.example.mvvmtemplate.screens


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtemplate.R
import com.example.mvvmtemplate.repository.FetchState
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import kotlinx.android.synthetic.main.fragment_social_feeds.*
import javax.inject.Inject

class SocialFeedsFragment : BaseFragment() {

    lateinit var socialFeedsViewModel: SocialFeedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        socialFeedsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SocialFeedsViewModel::class.java)
//        socialFeedsViewModel = ViewModelProviders.of(activity!!).get(SocialFeedsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_social_feeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(socialFeedsToolbar as Toolbar?)
        setHasOptionsMenu(true)
//        socialFeedsToolbar.apply {
//            inflateMenu(R.menu.social_feeds_menu)
//            setOnMenuItemClickListener {
//
//            }
//        }

        recyclerViewSocialFeeds.layoutManager = LinearLayoutManager(activity)
        recyclerViewSocialFeeds.setHasFixedSize(true)

        val adapter = SocialFeedsAdapter()
        recyclerViewSocialFeeds.adapter = adapter

        socialFeedsViewModel.socialfeedModels.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        socialFeedsViewModel.fetchFeedsState.observe(viewLifecycleOwner, Observer {
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
                val feedModel = adapter.getSocialFeedModelAt(viewHolder.adapterPosition)
                if(feedModel != null) socialFeedsViewModel.deleteSocialFeed(feedModel)
            }
        })
            .attachToRecyclerView(recyclerViewSocialFeeds)


        addFeedFab.setOnClickListener{
            findNavController().navigate(R.id.feedDetailFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.social_feeds_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteAllFeeds -> {
                socialFeedsViewModel.deleteAllSocialFeeds()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
