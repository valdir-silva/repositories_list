package com.valdirsilva.repositorieslist.presentation.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.valdirsilva.repositorieslist.R
import com.valdirsilva.repositorieslist.data.model.GitHubRepositoryModel
import com.valdirsilva.repositorieslist.utils.changeVisibility
import kotlinx.android.synthetic.main.repository_view.view.*
import java.util.*

class RepositoriesAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var repositoryList: MutableList<GitHubRepositoryModel> = LinkedList()
    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem: View = inflater.inflate(R.layout.repository_view, parent, false)

        return MovieViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repository = repositoryList[position]
        with(holder.itemView) {
            when (getItemViewType(position)) {
                ITEM -> {
                    username.text = repository.owner.login
                    name.text = repository.fullName
                    starIcon.changeVisibility(true)
                    starsNumber.text = repository.starsCount.toString()
                    forkIcon.changeVisibility(true)
                    forksNumber.text = repository.forkCount.toString()
                    Picasso.get()
                        .load(repository.owner.avatarUrl)
                        .error(R.drawable.ic_round_account_circle)
                        .into(
                            picture,
                            object : Callback {
                                override fun onSuccess() {
                                    progressBar.changeVisibility(false)
                                }

                                override fun onError(e: Exception?) {
                                    progressBar.changeVisibility(false)
                                }
                            }
                        )
                    if ((position - 1) % 2 == 0) {
                        fundo.setBackgroundResource(R.color.colorPrimary)
                    } else {
                        fundo.setBackgroundResource(R.color.colorPrimaryDark)
                    }
                }
                LOADING -> {
                    itemProgressBar?.changeVisibility(true)
                }
                else -> {
                    // do nothing
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == repositoryList.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        val position = repositoryList.size - 1
        val result = getItem(position)
        add(result)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = repositoryList.size - 1
        repositoryList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun add(repository: GitHubRepositoryModel) {
        repositoryList.add(repository)
        notifyItemInserted(repositoryList.size - 1)
    }

    fun addAll(repositoryResults: List<GitHubRepositoryModel>) {
        for (repository in repositoryResults) {
            add(repository)
        }
    }

    private fun getItem(position: Int): GitHubRepositoryModel {
        return repositoryList[position]
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }
}
