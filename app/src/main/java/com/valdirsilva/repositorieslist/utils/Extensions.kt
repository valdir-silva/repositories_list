package com.valdirsilva.repositorieslist.utils

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <M> MutableLiveData<M>.toLiveData(): LiveData<M> {
    return this
}

fun View.changeVisibility(isVisible: Boolean) {
    visibility = takeIf { isVisible }?.run { View.VISIBLE } ?: View.GONE
}

fun View.changeInvisibility(isInvisible: Boolean) {
    visibility = takeIf { isInvisible }?.run { View.INVISIBLE } ?: View.VISIBLE
}
