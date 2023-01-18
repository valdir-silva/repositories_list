package com.valdirsilva.repositorieslist.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OwnerModel(
    val id: Int,
    val login: String,
    val avatarUrl: String
) : Parcelable
