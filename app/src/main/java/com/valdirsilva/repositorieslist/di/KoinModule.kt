package com.valdirsilva.repositorieslist.di

import com.valdirsilva.repositorieslist.data.api.ApiService
import com.valdirsilva.repositorieslist.data.repository.ApiDataSource
import com.valdirsilva.repositorieslist.presentation.repositories.RepositoriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiService().getInstance(androidContext()) }
    single { ApiDataSource(get()) }

    viewModel { RepositoriesViewModel(ApiDataSource(get())) }
}
