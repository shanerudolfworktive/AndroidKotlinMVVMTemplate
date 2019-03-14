package com.example.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.SocialFeedModel
import com.example.mvvmtemplate.R
import kotlinx.android.synthetic.main.social_feeds_card.view.*

class SocialFeedsAdapter : RecyclerView.Adapter<SocialFeedViewHolder>() {

    var socialFeedModels: List<SocialFeedModel> = ArrayList()

    override fun getItemCount(): Int {
        return socialFeedModels.size
    }

    fun setSocialFeeds(socialFeedModels: List<SocialFeedModel>) {
        this.socialFeedModels = socialFeedModels;
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SocialFeedViewHolder, position: Int) {
        val socialFeedModel = socialFeedModels.get(position)
        holder.TextViewIId.setText("" + socialFeedModel.id)
        holder.textViewDescription.setText(socialFeedModel.userModel!!.description)
        holder.textViewName.setText(socialFeedModel.userModel!!.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.social_feeds_card, parent, false)
        return SocialFeedViewHolder(view)
    }
}

class SocialFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val TextViewIId =itemView.textViewId
    val textViewName = itemView.textViewName
    val textViewDescription = itemView.textViewDesription
}