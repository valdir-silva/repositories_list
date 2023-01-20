package com.valdirsilva.repositorieslist.presentation.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valdirsilva.repositorieslist.R
import com.valdirsilva.repositorieslist.data.ApiResults
import com.valdirsilva.repositorieslist.data.model.GitHubRepositoryModel
import com.valdirsilva.repositorieslist.data.repository.Repository
import com.valdirsilva.repositorieslist.utils.toLiveData

class RepositoriesViewModel(private val dataSource: Repository) : ViewModel() {

    private val _modelListLiveData: MutableLiveData<List<GitHubRepositoryModel>> = MutableLiveData()
    val modelListLiveData = _modelListLiveData.toLiveData()
    private val _errorMessageResLiveData: MutableLiveData<Int> = MutableLiveData()
    val errorMessageResLiveData = _errorMessageResLiveData.toLiveData()

    fun getRepositories(page: Int) {
        dataSource.getRepositories(page.toString()) { result: ApiResults ->
            when (result) {
                is ApiResults.Success -> {
                    _modelListLiveData.value = result.repositoryModelList.repositories
                }
                is ApiResults.ApiError -> {
                    if (result.statusCode == NOT_ALLOWED_ERROR) {
                        _errorMessageResLiveData.value = R.string.error_401
                    } else {
                        _errorMessageResLiveData.value = R.string.error_400_generic
                    }
                }
                is ApiResults.ServerError -> {
                    _errorMessageResLiveData.value = R.string.error_500_generic
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

    companion object {
        private const val NOT_ALLOWED_ERROR = 401
    }
}
