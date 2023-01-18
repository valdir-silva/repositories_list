package com.valdirsilva.repositorieslist.data.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.valdirsilva.repositorieslist.data.model.GitHubRepositoryModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepositoryResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "owner")
    val owner: OwnerResponse
) : Parcelable {
    fun getRepositoryModel() = GitHubRepositoryModel(
        id = id,
        name = name,
        fullName = fullName,
        owner = owner.getOwnerModel()
    )
}
