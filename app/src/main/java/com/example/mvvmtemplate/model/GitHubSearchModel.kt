package com.example.mvvmtemplate.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GitHubSearchResponseModel constructor(
    val total_count: Int = 0,
    val items: List<GitHubRepoModel> = emptyList(),
    val nextPage: Int? = null
)

@Entity(tableName = "GitHubRepoTable")
data class GitHubRepoModel @JvmOverloads constructor(
    @PrimaryKey val id: Long,
    @field:SerializedName("name") val name: String,
    val full_name: String,
    @field:SerializedName("description") val description: String?,
    val html_url: String,
    val stargazers_count: Int,
    val forks_count: Int,
    val language: String?,
    var insertTime: Long
)