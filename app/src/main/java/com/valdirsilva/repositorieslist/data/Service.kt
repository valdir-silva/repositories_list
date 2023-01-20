package com.valdirsilva.repositorieslist.data

import com.valdirsilva.repositorieslist.data.response.GitHubSearchResultResponse
import retrofit2.Call

interface Service {

    fun getRepositories(page: String): Call<GitHubSearchResultResponse>
}
