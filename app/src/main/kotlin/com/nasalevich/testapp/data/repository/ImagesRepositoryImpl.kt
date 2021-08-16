package com.nasalevich.testapp.data.repository

import android.util.Log
import com.nasalevich.testapp.base.ApiService
import com.nasalevich.testapp.data.entity.ImageEntity
import com.nasalevich.testapp.db.imagesstorage.ImagesStorage
import com.nasalevich.testapp.domain.model.ImageModel
import com.nasalevich.testapp.domain.repository.ImagesRepository
import com.nasalevich.testapp.extension.Constant.NUMBER_OF_ALL_ITEMS
import com.nasalevich.testapp.extension.Constant.NUMBER_OF_ITEMS_PER_PAGE
import com.nasalevich.testapp.extension.Constant.NUMBER_OF_PAGES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ImagesRepositoryImpl(
    private val imagesService: ApiService<List<ImageEntity>, Int>,
    private val imagesStorage: ImagesStorage,
) : ImagesRepository, CoroutineScope {

    private val coroutineScopeJob = Job()
    private var reloadImagesJob: Job? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineScopeJob

    init {
        loadAllImages()
    }

    override fun fetchImagesAsFlow(): Flow<List<ImageModel>> {
        return imagesStorage.getAllAsFlow()
    }

    override fun addImage() {
        launch {
            loadRandomImages(1)
        }
    }

    override fun reloadAllImages() {
        reload()
    }

    override fun clear() {
        coroutineScopeJob.cancel()
    }

    private fun reload() {
        imagesStorage.deleteAll()

        reloadImagesJob?.cancel()
        reloadImagesJob = launch {
            repeat(NUMBER_OF_PAGES) {
                loadRandomImages(NUMBER_OF_ITEMS_PER_PAGE)
            }
        }
    }

    private fun loadAllImages() {
        if (imagesStorage.getAll().size >= NUMBER_OF_ALL_ITEMS) return

        reload()
    }

    private suspend fun loadRandomImages(count: Int) {
        try {
            imagesService.load(count).forEach {
                imagesStorage.insert(it)
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("ImagesRepositoryImpl", it) }
        }
    }
}
