package com.nasalevich.testapp.db.imagesstorage

import com.nasalevich.testapp.Database
import com.nasalevich.testapp.ImageQueries
import com.nasalevich.testapp.data.entity.ImageEntity
import com.nasalevich.testapp.domain.model.ImageModel
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

class ImagesStorageImpl(
    database: Database,
) : ImagesStorage {

    private val dbQuery: ImageQueries = database.imageQueries

    private val imageMapper: (id: Long, url: String) -> ImageModel = { id, url -> ImageModel(id.toInt(), url) }

    override fun get(id: Int): ImageModel {
        return dbQuery.get(
            id.toLong(),
            mapper = imageMapper,
        ).executeAsOne()
    }

    override fun getAll(): List<ImageModel> {
        return dbQuery.getAll(
            mapper = imageMapper,
        ).executeAsList()
    }

    override fun getAllAsFlow(): Flow<List<ImageModel>> {
        return dbQuery.getAll(
            mapper = imageMapper,
        ).asFlow().mapToList()
    }

    override fun insert(imageEntity: ImageEntity) {
        imageEntity.url?.let { dbQuery.insert(it) }
    }

    override fun deleteAll() {
        dbQuery.transaction {
            dbQuery.deleteAll()
        }
    }
}
