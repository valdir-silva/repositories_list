package com.valdirsilva.repositorieslist.data.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.valdirsilva.repositorieslist.data.model.OwnerModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OwnerResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "login")
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String
) : Parcelable {
    fun getOwnerModel() = OwnerModel(
        id = id,
        login = login,
        avatarUrl = avatarUrl
    )
}
