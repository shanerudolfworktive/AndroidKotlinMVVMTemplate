package com.example.mvvmtemplate


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mvvmtemplate.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_feed_detail.*
import kotlinx.android.synthetic.main.fragment_social_feeds.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FeedDetailFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigationBar()
    }

    fun setUpNavigationBar(){
        (activity as AppCompatActivity).setSupportActionBar(feedDetailToolbar as Toolbar?)
        (feedDetailToolbar as Toolbar?)?.title = "Feed Detail"
        (feedDetailToolbar as Toolbar?)?.setNavigationIcon(R.drawable.ic_close)
        (feedDetailToolbar as Toolbar?)?.setNavigationOnClickListener{
            findNavController().popBackStack()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.saveFeed -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
