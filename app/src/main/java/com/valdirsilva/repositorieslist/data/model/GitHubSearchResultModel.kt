package com.valdirsilva.repositorieslist.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubSearchResultModel(
    val repositories: List<GitHubRepositoryModel>
) : Parcelable
