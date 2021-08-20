package com.nasalevich.testapp.data.service

import com.nasalevich.testapp.base.ApiService
import com.nasalevich.testapp.data.entity.ImageEntity
import io.ktor.client.*
import io.ktor.client.request.*

class ImagesService(
    private val client: HttpClient,
) : ApiService<Int, List<ImageEntity>> {

    override suspend fun load(param: Int): List<ImageEntity> = client.get("/images/search") {
        parameter("limit", param)
        parameter("size", "small")
    }
}
