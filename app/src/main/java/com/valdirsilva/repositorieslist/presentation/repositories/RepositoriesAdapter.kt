package com.valdirsilva.repositorieslist.presentation.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.valdirsilva.repositorieslist.R
import com.valdirsilva.repositorieslist.data.model.GitHubRepositoryModel
import kotlinx.android.synthetic.main.repositorie_view.view.*

class RepositoriesAdapter(private var repositories: ArrayList<GitHubRepositoryModel>) :
    RecyclerView.Adapter<RepositoriesAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(repository: GitHubRepositoryModel) {
            itemView.apply {
                username.text = repository.owner.login
                name.text = repository.fullName

                Picasso.get()
                    .load(repository.owner.avatarUrl)
                    .error(R.drawable.ic_round_account_circle)
                    .into(
                        itemView.picture,
                        object : Callback {
                            override fun onSuccess() {
                                itemView.progressBar.visibility = View.GONE
                            }

                            override fun onError(e: Exception?) {
                                itemView.progressBar.visibility = View.GONE
                            }
                        }
                    )

                if ((adapterPosition - 1) % 2 == 0) {
                    fundo.setBackgroundResource(R.color.colorPrimary)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.repositorie_view, parent, false)
        )

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    fun addRepositories(repositories: List<GitHubRepositoryModel>) {
        this.repositories.apply {
            clear()
            addAll(repositories)
        }
    }
}
