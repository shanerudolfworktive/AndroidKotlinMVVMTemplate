package com.example.mvvmtemplate.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtemplate.databinding.SocialFeedsCardBinding
import com.example.mvvmtemplate.model.SocialFeedModel

class SocialFeedsAdapter : ListAdapter<SocialFeedModel, SocialFeedViewHolder>(DiffCallback()) {

    var inflater: LayoutInflater? = null
    override fun onBindViewHolder(holder: SocialFeedViewHolder, position: Int) {
        val socialFeedModel = getItem(position)
        holder.bind(socialFeedModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialFeedViewHolder {
        if(inflater == null) inflater = LayoutInflater.from(parent.context)
        return SocialFeedViewHolder(SocialFeedsCardBinding.inflate(inflater!!, parent, false))
    }

    fun getSocialFeedModelAt(position: Int): SocialFeedModel? {
        return getItem(position)
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

class SocialFeedViewHolder(val binding: SocialFeedsCardBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: SocialFeedModel?) {
        binding.feedModel = model
    }
}
