package com.valdirsilva.repositorieslist.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepositoryModel(
    val id: Int,
    val name: String,
    val fullName: String,
    val owner: OwnerModel
) : Parcelable
