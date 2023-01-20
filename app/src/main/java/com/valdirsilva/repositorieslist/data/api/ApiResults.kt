package com.valdirsilva.repositorieslist.data.api

import com.valdirsilva.repositorieslist.data.model.GitHubSearchResultModel

sealed class ApiResults {
    class Success(val repositoryModelList: GitHubSearchResultModel) : ApiResults()
    class Error(val statusCode: Int? = null, val errorMessage: String) : ApiResults()
}
