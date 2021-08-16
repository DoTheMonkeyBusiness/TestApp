package com.nasalevich.testapp.presentation

sealed class HomeIntent {

    object AddImage : HomeIntent()

    object ReloadAll : HomeIntent()
}
