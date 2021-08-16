package com.nasalevich.testapp.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageEntity(

    @SerialName("url")
    val url: String?,
)