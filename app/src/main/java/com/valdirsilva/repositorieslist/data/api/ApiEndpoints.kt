package com.valdirsilva.repositorieslist.data.api

import com.valdirsilva.repositorieslist.data.response.GitHubSearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoints : Service {

    @GET("repositories?q=language:kotlin&sort=stars")
    override fun getRepositories(
        @Query("page") page: String
    ): Call<GitHubSearchResultResponse>
}
