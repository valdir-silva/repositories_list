package com.valdirsilva.repositorieslist.presentation.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valdirsilva.repositorieslist.data.api.ApiResults
import com.valdirsilva.repositorieslist.data.model.GitHubRepositoryModel
import com.valdirsilva.repositorieslist.data.repository.Repository
import com.valdirsilva.repositorieslist.utils.toLiveData

class RepositoriesViewModel(private val dataSource: Repository) : ViewModel() {

    private val _modelListLiveData: MutableLiveData<List<GitHubRepositoryModel>> = MutableLiveData()
    val modelListLiveData = _modelListLiveData.toLiveData()
    private val _errorMessageResLiveData: MutableLiveData<String> = MutableLiveData()
    val errorMessageResLiveData = _errorMessageResLiveData.toLiveData()

    fun getRepositories(page: Int) {
        dataSource.getRepositories(page.toString()) { result: ApiResults ->
            when (result) {
                is ApiResults.Success -> {
                    _modelListLiveData.value = result.repositoryModelList.repositories
                }
                is ApiResults.Error -> {
                    _errorMessageResLiveData.value =
                        "${result.statusCode} ${result.errorMessage}"
                }
            }
        }
    }

    class ViewModelFactory(private val dataSource: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepositoriesViewModel::class.java)) {
                return RepositoriesViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknow ViewModel class")
        }
    }
}
