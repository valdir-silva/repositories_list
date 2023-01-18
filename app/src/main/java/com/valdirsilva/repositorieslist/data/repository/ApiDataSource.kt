package com.valdirsilva.repositorieslist.data.repository

import com.valdirsilva.repositorieslist.BuildConfig
import com.valdirsilva.repositorieslist.data.ApiResults
import com.valdirsilva.repositorieslist.data.ApiService
import com.valdirsilva.repositorieslist.data.Service
import com.valdirsilva.repositorieslist.data.response.GitHubSearchResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiDataSource : Repository {

    private val service: Service =
        if (BuildConfig.BUILD_TYPE == MOCK_VARIANT_KEY) ApiService.mockEndpoints else ApiService.apiEndpoints

    override fun getRepositories(resultCallback: (result: ApiResults) -> Unit) {
        service.getRepositories().enqueue(object : Callback<GitHubSearchResultResponse> {
            override fun onResponse(
                call: Call<GitHubSearchResultResponse>,
                response: Response<GitHubSearchResultResponse>
            ) {
                when {
                    response.isSuccessful -> {
//                        val modelList: MutableList<GitHubSearchResultModel> = mutableListOf()
//
//                        response.body()?.let { bodyResponse ->
//                            for (result in bodyResponse) {
//                                val model = result.getRepositoryModel()
//                                modelList.add(model)
//                            }
//                        }

                        response.body()?.let { result ->
                            resultCallback(ApiResults.Success(result.getGitHubSearchResultModel()))
                        } ?: run {
                            resultCallback(ApiResults.ApiError(0)) // TODO rever o que passar
                        }
                    }
                    else -> resultCallback(ApiResults.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<GitHubSearchResultResponse>, t: Throwable) {
                resultCallback(ApiResults.ServerError)
            }
        })
    }

    private companion object {
        const val MOCK_VARIANT_KEY = "mock"
    }
}
