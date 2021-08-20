package com.nasalevich.testapp.base

interface ApiService<in P, out R> {

    suspend fun load(param: P): R
}
