package com.example.mvvmtemplate.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.R
import kotlinx.android.synthetic.main.social_feeds_card.view.*

class SocialFeedsAdapter : ListAdapter<SocialFeedModel, SocialFeedViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: SocialFeedViewHolder, position: Int) {
        val socialFeedModel = getItem(position)
        holder.TextViewIId.setText("" + socialFeedModel.id)
        holder.textViewDescription.setText(socialFeedModel.user?.description?: "null description")
        holder.textViewName.setText(socialFeedModel.user?.name?: "null name")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.social_feeds_card, parent, false)
        return SocialFeedViewHolder(view)
    }
}

class DiffCallback : DiffUtil.ItemCallback<SocialFeedModel>() {
    override fun areItemsTheSame(oldItem: SocialFeedModel, newItem: SocialFeedModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SocialFeedModel, newItem: SocialFeedModel): Boolean {
        return oldItem.user?.description == newItem.user?.description
                && oldItem.user?.id == newItem.user?.id
                && oldItem.user?.name == newItem.user?.name
    }

}

class SocialFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val TextViewIId =itemView.textViewId
    val textViewName = itemView.textViewName
    val textViewDescription = itemView.textViewDesription
}