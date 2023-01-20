package com.valdirsilva.repositorieslist.data.repository

import com.valdirsilva.repositorieslist.BuildConfig
import com.valdirsilva.repositorieslist.data.api.ApiResults
import com.valdirsilva.repositorieslist.data.api.ApiService
import com.valdirsilva.repositorieslist.data.api.Service
import com.valdirsilva.repositorieslist.data.response.GitHubSearchResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiDataSource(apiService: ApiService) : Repository {

    private val service: Service =
        if (BuildConfig.BUILD_TYPE == MOCK_VARIANT_KEY) apiService.mockEndpoints else apiService.apiEndpoints

    override fun getRepositories(page: String, resultCallback: (result: ApiResults) -> Unit) {
        service.getRepositories(page).enqueue(object : Callback<GitHubSearchResultResponse> {
            override fun onResponse(
                call: Call<GitHubSearchResultResponse>,
                response: Response<GitHubSearchResultResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        response.body()?.let { result ->
                            resultCallback(ApiResults.Success(result.getGitHubSearchResultModel()))
                        } ?: run {
                            resultCallback(
                                ApiResults.Error(
                                    statusCode = response.code(),
                                    errorMessage = EMPTY_BODY
                                )
                            )
                        }
                    }
                    else -> resultCallback(
                        ApiResults.Error(
                            statusCode = response.code(),
                            errorMessage = response.message()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<GitHubSearchResultResponse>, t: Throwable) {
                resultCallback(ApiResults.Error(errorMessage = t.message.toString()))
            }
        })
    }

    private companion object {
        const val MOCK_VARIANT_KEY = "mock"
        const val EMPTY_BODY = "response has no content"
    }
}
