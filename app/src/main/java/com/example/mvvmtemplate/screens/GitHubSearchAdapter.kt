package com.example.mvvmtemplate.screens

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtemplate.databinding.GithubSearchItemBinding
import com.example.mvvmtemplate.model.GitHubRepoModel

class GitHubSearchAdapter : PagedListAdapter<GitHubRepoModel, RepoViewHolder>(REPO_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repoItem = getItem(position)
        holder.bind(repoItem)
    }
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GitHubRepoModel>() {
            override fun areItemsTheSame(oldItem: GitHubRepoModel, newItem: GitHubRepoModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GitHubRepoModel, newItem: GitHubRepoModel): Boolean =
                oldItem.full_name == newItem.full_name
        }
    }
}

class RepoViewHolder(private val binding: GithubSearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

    var repo: GitHubRepoModel? = null
    init {
        binding.root.setOnClickListener {
            repo?.html_url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                it.context.startActivity(intent)
            }
        }
    }

    fun bind(repo: GitHubRepoModel?) {
        this.repo = repo
        binding.githubRepoModel = repo
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return RepoViewHolder(GithubSearchItemBinding.inflate(inflater, parent, false))
        }
    }
}