package com.example.mvvmtemplate.screens

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtemplate.R
import com.example.mvvmtemplate.model.GitHubRepoModel

class GitHubSearchAdapter : PagedListAdapter<GitHubRepoModel, RecyclerView.ViewHolder>(REPO_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as RepoViewHolder).bind(repoItem)
        }
    }
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GitHubRepoModel>() {
            override fun areItemsTheSame(oldItem: GitHubRepoModel, newItem: GitHubRepoModel): Boolean =
                oldItem.full_name == newItem.full_name

            override fun areContentsTheSame(oldItem: GitHubRepoModel, newItem: GitHubRepoModel): Boolean =
                oldItem == newItem
        }
    }
}

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)

    private var repo: GitHubRepoModel? = null

    init {
        view.setOnClickListener {
            repo?.html_url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(repo: GitHubRepoModel?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: GitHubRepoModel) {
        this.repo = repo
        name.text = repo.full_name

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.description != null) {
            description.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.github_search_item, parent, false)
            return RepoViewHolder(view)
        }
    }
}