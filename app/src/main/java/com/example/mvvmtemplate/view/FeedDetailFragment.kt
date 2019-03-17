package com.example.mvvmtemplate.view


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.mvvmtemplate.R
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.util.ViewUtils
import com.example.mvvmtemplate.viewmodel.FeedDetailViewModel
import kotlinx.android.synthetic.main.fragment_feed_detail.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FeedDetailFragment : BaseFragment() {

    lateinit var viewModel: FeedDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(FeedDetailViewModel::class.java)
    }

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
                val name = nameEditText.text.toString();
                val description = descriptionEditText.text.toString();
                if (name.isBlank() || description.isBlank()) {
                    Toast.makeText(activity, "please fill name and description", Toast.LENGTH_SHORT).show()
                    return false
                }
                val userModel = UserModel(name=name, description=description)
                viewModel.insertSocialFeed(SocialFeedModel(user = userModel))
                ViewUtils.hideSoftKeyboard(activity!!, view!!)
                findNavController().popBackStack()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
