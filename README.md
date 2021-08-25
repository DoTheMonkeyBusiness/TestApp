# Test App

Just a simple gallery with cats.

## Credentials

### THE CAT API Key
Cat API key for this app is required to build the project,
you can get it on their [website](https://thecatapi.com/signup).

To make it works you need to add this key to the gradle `local.properties` file using `THE_CAT_API_KEY` key:
```properties
THE_CAT_API_KEY=<API_KEY>
```

### Keystore
To build the project, you also need to add the `keystore` properties to the gradle `local.properties` file.
Please add empty strings to build debug version of the app.
```properties
storePassword=""
keyAlias=""
keyPassword=""
```

### Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
* [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
* [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
* [Ktor client library](https://github.com/ktorio/ktor)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* [Koin](https://github.com/InsertKoinIO/koin)
* [SQLDelight](https://github.com/cashapp/sqldelight)
* [Jetpack Compose](https://developer.android.com/jetpack/compose)
