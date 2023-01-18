package com.valdirsilva.repositorieslist.presentation.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.valdirsilva.repositorieslist.data.model.GitHubRepositoryModel
import com.valdirsilva.repositorieslist.data.repository.ApiDataSource
import com.valdirsilva.repositorieslist.databinding.RepositoriesFragmentBinding
import com.valdirsilva.repositorieslist.utils.changeVisibility

class RepositoriesFragment : Fragment() {

    private var _binding: RepositoriesFragmentBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: RepositoriesViewModel
    private lateinit var adapter: RepositoriesAdapter

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

        adapter = RepositoriesAdapter(arrayListOf())
        viewModel = RepositoriesViewModel.ViewModelFactory(ApiDataSource())
            .create(RepositoriesViewModel::class.java)

        viewModel.getRepositories()
        binding?.let {
            setupBinding(it)
            setupObservers(it)
        }
    }

    private fun setupBinding(binding: RepositoriesFragmentBinding) {
        with(binding) {
            rvRepositories.layoutManager = LinearLayoutManager(context)
            adapter = RepositoriesAdapter(arrayListOf())
            rvRepositories.addItemDecoration(
                DividerItemDecoration(
                    rvRepositories.context,
                    (rvRepositories.layoutManager as LinearLayoutManager).orientation
                )
            )
            rvRepositories.adapter = adapter
        }
    }

    private fun setupObservers(binding: RepositoriesFragmentBinding) {
        with(viewModel) {
            modelListLiveData.observe(viewLifecycleOwner) { repository ->
                binding.tvTitle.changeVisibility(true)
                binding.repositoriesListProgressBar.changeVisibility(false)
                retrieveList(repository as MutableList<GitHubRepositoryModel>)
            }
            errorMessageResLiveData.observe(viewLifecycleOwner) { errorRes ->
                binding.repositoriesListProgressBar.changeVisibility(false)
                binding.rvRepositories.changeVisibility(false)
                binding.tvError.apply {
                    changeVisibility(true)
                    text = resources.getString(errorRes)
                }
            }
        }
    }

    private fun retrieveList(repositories: MutableList<GitHubRepositoryModel>) {
        repositories.sortBy { it.name }
        adapter.apply {
            addRepositories(repositories)
            notifyDataSetChanged()
        }
    }
}
