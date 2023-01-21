package com.valdirsilva.repositorieslist.presentation.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.valdirsilva.repositorieslist.data.api.ApiResults
import com.valdirsilva.repositorieslist.data.model.GitHubRepositoryModel
import com.valdirsilva.repositorieslist.data.repository.Repository
import com.valdirsilva.repositorieslist.utils.toLiveData

class RepositoriesViewModel(private val dataSource: Repository) : ViewModel() {

    private val _repositoryListLiveData: MutableLiveData<List<GitHubRepositoryModel>> = MutableLiveData()
    val repositoryListLiveData = _repositoryListLiveData.toLiveData()
    private val _errorMessageResLiveData: MutableLiveData<String> = MutableLiveData()
    val errorMessageResLiveData = _errorMessageResLiveData.toLiveData()

    fun getRepositories(page: Int) {
        dataSource.getRepositories(page.toString()) { result: ApiResults ->
            when (result) {
                is ApiResults.Success -> {
                    _repositoryListLiveData.value = result.repositoryModelList.repositories
                }
                is ApiResults.Error -> {
                    _errorMessageResLiveData.value =
                        "${result.statusCode ?: ""} ${result.errorMessage}"
                }
            }
        }
    }

    fun setRepositoryList(userList: List<GitHubRepositoryModel>) {
        _repositoryListLiveData.value = userList
    }
}
