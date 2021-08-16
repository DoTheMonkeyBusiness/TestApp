package com.nasalevich.testapp.presentation

import com.nasalevich.testapp.domain.model.ImageModel

sealed class CellModel {

    object Placeholder : CellModel()

    data class HasImage(val imageModel: ImageModel) : CellModel()
}
