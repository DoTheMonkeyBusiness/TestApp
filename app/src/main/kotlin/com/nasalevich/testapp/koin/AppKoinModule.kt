package com.nasalevich.testapp.koin

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.Logger
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
            imagesStorage = get(),
            logger = get(),
        )
    }

    single { ApiClientProvider.getHttpClient() }
    single { Database(AndroidSqliteDriver(Database.Schema, androidContext(), "testApp.db")) }
    single { Kermit(logger = get()) }

    single<ImagesStorage> { ImagesStorageImpl(database = get()) }
    single<Logger> { CommonLogger() }

    viewModel {
        HomeViewModel(
            repository = get()
        )
    }
}
