package com.valdirsilva.repositorieslist.data

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.valdirsilva.repositorieslist.data.response.GitHubSearchResultResponse
import retrofit2.Call
import retrofit2.http.GET

interface MockEndpoints : Service {

    @MockResponse(body = "repositories.json")
    @Mock
    @GET("/")
    override fun getRepositories(): Call<GitHubSearchResultResponse>
}
