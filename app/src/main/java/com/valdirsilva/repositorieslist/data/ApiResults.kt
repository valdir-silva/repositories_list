package com.valdirsilva.repositorieslist.data

import com.valdirsilva.repositorieslist.data.model.GitHubSearchResultModel

sealed class ApiResults {
    class Success(val repositoryModelList: GitHubSearchResultModel) : ApiResults()
    class ApiError(val statusCode: Int) : ApiResults()
    object ServerError : ApiResults()
}
