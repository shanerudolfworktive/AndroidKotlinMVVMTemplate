package com.example.mvvmtemplate


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtemplate.repository.FetchState
import com.example.mvvmtemplate.view.SocialFeedsAdapter
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_social_feeds.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SocialFeedsFragment : Fragment() {

    lateinit var socialFeedsViewModel: SocialFeedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        socialFeedsViewModel = ViewModelProviders.of(activity!!).get(SocialFeedsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_feeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(socialFeedsToolbar as Toolbar?)
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
