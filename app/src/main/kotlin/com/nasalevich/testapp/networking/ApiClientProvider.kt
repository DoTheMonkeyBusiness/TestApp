package com.nasalevich.testapp.networking

import com.nasalevich.testapp.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

object ApiClientProvider {

    fun getHttpClient() = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    allowStructuredMapKeys = true
                    isLenient = true
                }
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("KTOR: $message")
                }
            }
        }
        defaultRequest {
            url.protocol = URLProtocol.HTTPS
            host = "api.thecatapi.com/v1"
            headers {
                "x-api-key" to BuildConfig.THE_CAT_API_KEY
            }
        }
    }
}
