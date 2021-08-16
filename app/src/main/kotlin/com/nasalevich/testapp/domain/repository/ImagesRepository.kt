package com.nasalevich.testapp.domain.repository

import com.nasalevich.testapp.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    fun fetchImagesAsFlow(): Flow<List<ImageModel>>

    fun addImage()

    fun reloadAllImages()

    fun clear()
}
