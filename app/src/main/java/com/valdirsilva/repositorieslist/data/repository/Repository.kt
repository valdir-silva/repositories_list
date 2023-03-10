package com.valdirsilva.repositorieslist.data.repository

import com.valdirsilva.repositorieslist.data.api.ApiResults

interface Repository {

    fun getRepositories(page: String, resultCallback: (result: ApiResults) -> Unit)
}
