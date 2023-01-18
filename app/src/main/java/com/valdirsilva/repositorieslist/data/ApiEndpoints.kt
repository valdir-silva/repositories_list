package com.valdirsilva.repositorieslist.data

import com.valdirsilva.repositorieslist.data.response.GitHubSearchResultResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoints : Service {

    @GET("repositories?q=language:kotlin&sort=stars&page=1")
    override fun getRepositories(): Call<GitHubSearchResultResponse>
}
