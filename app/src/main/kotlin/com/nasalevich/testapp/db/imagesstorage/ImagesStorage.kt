package com.nasalevich.testapp.db.imagesstorage

import com.nasalevich.testapp.data.entity.ImageEntity
import com.nasalevich.testapp.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImagesStorage {

    fun get(id: Int): ImageModel

    fun getAll(): List<ImageModel>

    fun getAllAsFlow(): Flow<List<ImageModel>>

    fun insert(imageEntity: ImageEntity)

    fun deleteAll()
}
