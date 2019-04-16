package com.example.mvvmtemplate.viewmodel

import com.example.mvvmtemplate.repository.GitHubSearchRepository

class GitHubSearchViewModel constructor(
    private val repository: GitHubSearchRepository = GitHubSearchRepository.getInstance()
): BaseViewModel(){

    val gitHubRepoModels = repository.gitHubRepoModels

    fun gitHubSearch(query: String) {
        repository.gitHubSearch(query);
    }


}