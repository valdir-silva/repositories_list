package com.valdirsilva.repositorieslist.data.repository

import com.valdirsilva.repositorieslist.data.ApiResults

interface Repository {

    fun getRepositories(resultCallback: (result: ApiResults) -> Unit)
}
