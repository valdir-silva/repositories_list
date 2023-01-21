package com.valdirsilva.repositorieslist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.valdirsilva.repositorieslist.data.api.ApiResults
import com.valdirsilva.repositorieslist.data.model.GitHubRepositoryModel
import com.valdirsilva.repositorieslist.data.model.GitHubSearchResultModel
import com.valdirsilva.repositorieslist.data.model.OwnerModel
import com.valdirsilva.repositorieslist.data.repository.Repository
import com.valdirsilva.repositorieslist.presentation.repositories.RepositoriesViewModel
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

class RepositoriesViewModelTest : KoinTest {

    private val viewModel: RepositoriesViewModel by inject()

    @get:Rule
    val rule = InstantTaskExecutorRule()

//    @Mock
//    private lateinit var userModelListLiveDataObserver: Observer<List<GitHubSearchResultModel>>

    fun setUp(mockRepository: Repository) {
        startKoin {
            modules(
                module {
                    viewModel { RepositoriesViewModel(mockRepository) }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `when viewModel getRepositories get success then sets modelListLiveData`() {
        // Arrange
        val mockResultSuccess = MockRepository(ApiResults.Success(repositorySearchResult))
        setUp(mockResultSuccess)

        // Act
        viewModel.getRepositories(1)
        val result = viewModel.modelListLiveData.value

        // Assert
        assert(result == repositorySearchResult.repositories)
    }

    @Test
    fun `when viewModel getRepositories get api 400 error then sets errorMessageResLiveData`() {
        // Arrange
        val mockResultApiError = MockRepository(
            ApiResults.Error(
                statusCode = 400,
                errorMessage = "error"
            )
        )
        setUp(mockResultApiError)

        // Act
        viewModel.getRepositories(1)
        val result = viewModel.errorMessageResLiveData.value

        // Assert
        assert(result != null)
        result?.let { assert(it.isNotEmpty()) }
    }
}

class MockRepository(private val result: ApiResults) : Repository {
    override fun getRepositories(page: String, resultCallback: (result: ApiResults) -> Unit) {
        resultCallback(result)
    }
}

val repositorySearchResult = GitHubSearchResultModel(
    repositories = listOf(
        GitHubRepositoryModel(
            id = 1,
            name = "Valdir Alfredo",
            fullName = "valdir_aosilva",
            starsCount = 123,
            forkCount = 123,
            owner = OwnerModel(
                id = 2,
                login = "valdirsilva",
                avatarUrl = "https://randomuser.me/api/portraits/men/2.jpg"
            )
        )
    )
)
