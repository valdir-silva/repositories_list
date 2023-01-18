package com.valdirsilva.repositorieslist.data.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.valdirsilva.repositorieslist.data.model.GitHubSearchResultModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubSearchResultResponse(
    @Json(name = "items")
    val repositories: List<GitHubRepositoryResponse>
) : Parcelable {
    fun getGitHubSearchResultModel() = GitHubSearchResultModel(
        repositories = repositories.map { it.getRepositoryModel() }
    )
}
