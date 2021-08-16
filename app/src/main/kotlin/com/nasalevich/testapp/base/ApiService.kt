package com.nasalevich.testapp.base

interface ApiService<T, P> {

    suspend fun load(param: P): T
}
