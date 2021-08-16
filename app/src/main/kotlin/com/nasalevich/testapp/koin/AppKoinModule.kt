package com.nasalevich.testapp.koin

import com.nasalevich.testapp.Database
import com.nasalevich.testapp.data.repository.ImagesRepositoryImpl
import com.nasalevich.testapp.data.service.ImagesService
import com.nasalevich.testapp.db.imagesstorage.ImagesStorage
import com.nasalevich.testapp.db.imagesstorage.ImagesStorageImpl
import com.nasalevich.testapp.domain.repository.ImagesRepository
import com.nasalevich.testapp.networking.ApiClientProvider
import com.nasalevich.testapp.presentation.HomeViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppKoinModule = module {

    factory<ImagesRepository> {
        ImagesRepositoryImpl(
            imagesService = ImagesService(
                client = get(),
            ),
            imagesStorage = get()
        )
    }

    single { ApiClientProvider.getHttpClient() }
    single { Database(AndroidSqliteDriver(Database.Schema, androidContext(), "testApp.db")) }

    single<ImagesStorage> { ImagesStorageImpl(database = get()) }

    viewModel {
        HomeViewModel(
            repository = get()
        )
    }
}
