package com.nasalevich.testapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasalevich.testapp.domain.repository.ImagesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ImagesRepository
) : ViewModel() {

    val userIntent = Channel<HomeIntent>(Channel.UNLIMITED)

    val state = repository.fetchImagesAsFlow().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is HomeIntent.AddImage -> repository.addImage()
                    is HomeIntent.ReloadAll -> repository.reloadAllImages()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        repository.clear()
    }
}
