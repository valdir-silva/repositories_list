package com.valdirsilva.repositorieslist.presentation.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.valdirsilva.repositorieslist.data.model.GitHubRepositoryModel
import com.valdirsilva.repositorieslist.databinding.RepositoriesFragmentBinding
import com.valdirsilva.repositorieslist.utils.PaginationScrollListener
import com.valdirsilva.repositorieslist.utils.changeVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesFragment : Fragment() {

    private var _binding: RepositoriesFragmentBinding? = null
    private val binding get() = _binding

    private val viewModel: RepositoriesViewModel by viewModel()
    private lateinit var adapter: RepositoriesAdapter

    private var isLoadingRv = false
    private var isLastPageRv = false
    private val pageStart = 1
    private var currentPage = pageStart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RepositoriesFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RepositoriesAdapter()

        binding?.let {
            setupBinding(it)
            setupObservers(it)
        }
        initialAdapterSetup(savedInstanceState)
    }

    private fun initialAdapterSetup(savedInstanceState: Bundle?) {
        var repositoryList: ArrayList<GitHubRepositoryModel>? = null
        if (savedInstanceState != null) {
            repositoryList = savedInstanceState.getParcelableArrayList(REPOSITORY_LIST_KEY)
        }
        repositoryList?.let {
            adapter.removeAll()
            viewModel.setRepositoryList(it)
        }
            ?: viewModel.getRepositories(pageStart)
    }

    private fun setupBinding(binding: RepositoriesFragmentBinding) {
        with(binding) {
            repositoriesListProgressBar.changeVisibility(true)
            rvRepositories.changeVisibility(false)
            rvRepositories.layoutManager = LinearLayoutManager(context)
            adapter = RepositoriesAdapter()
            rvRepositories.addItemDecoration(
                DividerItemDecoration(
                    rvRepositories.context,
                    (rvRepositories.layoutManager as LinearLayoutManager).orientation
                )
            )
            rvRepositories.adapter = adapter

            rvRepositories.addOnScrollListener(object :
                    PaginationScrollListener(rvRepositories.layoutManager as LinearLayoutManager) {
                    override fun loadMoreItems() {
                        isLoadingRv = true
                        currentPage++
                        adapter.addLoadingFooter()
                        viewModel.getRepositories(currentPage)
                        if (currentPage == TOTAL_PAGES) isLastPageRv = true
                    }

                    override val isLastPage: Boolean
                        get() = isLastPageRv
                    override val isLoading: Boolean
                        get() = isLoadingRv
                })
        }
    }

    private fun setupObservers(binding: RepositoriesFragmentBinding) {
        with(viewModel) {
            repositoryListLiveData.observe(viewLifecycleOwner) { repositoryList ->
                binding.tvTitle.changeVisibility(true)
                binding.rvRepositories.changeVisibility(true)
                binding.repositoriesListProgressBar.changeVisibility(false)
                isLoadingRv = false
                if (currentPage > 1) adapter.removeLoadingFooter()
                adapter.addAll(repositoryList)
            }
            errorMessageResLiveData.observe(viewLifecycleOwner) { errorMessage ->
                binding.repositoriesListProgressBar.changeVisibility(false)
                binding.rvRepositories.changeVisibility(false)
                binding.tvError.apply {
                    changeVisibility(true)
                    text = errorMessage
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // TODO com essa implementacao temos um problema de consistencia dos dados do viewModel
        val repositoryList = adapter.getRepositoryList()
        outState.putParcelableArrayList(
            REPOSITORY_LIST_KEY,
            ArrayList(repositoryList)
        )
    }

    private companion object {
        const val TOTAL_PAGES = 30
        const val REPOSITORY_LIST_KEY = "REPOSITORY_LIST_KEY"
    }
}
