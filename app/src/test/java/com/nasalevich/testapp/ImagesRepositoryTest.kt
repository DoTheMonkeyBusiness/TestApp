package com.nasalevich.testapp

import co.touchlab.kermit.Kermit
import com.nasalevich.testapp.base.ApiService
import com.nasalevich.testapp.data.entity.ImageEntity
import com.nasalevich.testapp.data.repository.ImagesRepositoryImpl
import com.nasalevich.testapp.db.imagesstorage.ImagesStorage
import com.nasalevich.testapp.db.imagesstorage.ImagesStorageImpl
import com.nasalevich.testapp.domain.model.ImageModel
import com.nasalevich.testapp.extension.Constant
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExperimentalCoroutinesApi
class ImagesRepositoryTest {

    @RelaxedMockK
    private lateinit var imagesService: ApiService<Int, List<ImageEntity>>

    @RelaxedMockK
    private lateinit var logger: Kermit

    @InjectMockKs
    private lateinit var imagesStorage: ImagesStorage

    @InjectMockKs
    private lateinit var imagesRepository: ImagesRepositoryImpl

    private val mockedImageEntity = ImageEntity("test")
    private val mockedImageModel = ImageModel(1, "test")

    @BeforeEach
    fun before() {
        val driver = createTestSqlDriver()
        val database = Database(driver)

        imagesStorage = ImagesStorageImpl(database)

        MockKAnnotations.init(this)
    }

    @ParameterizedTest
    @ValueSource(ints = [5, 10, 23])
    fun testNotEmptyDB(dbSize: Int) = runBlocking {

        val numItemsBeforeInsertion = imagesRepository.fetchImagesAsFlow().first().size

        repeat(dbSize) {
            imagesStorage.insert(mockedImageEntity)
        }

        val numItemsAfterInsertion = imagesRepository.fetchImagesAsFlow().first().size

        assertEquals(0, numItemsBeforeInsertion)
        assertEquals(dbSize, numItemsAfterInsertion)
    }

    @Test
    fun testAddImage() = runBlocking {
        val numberOfImages = 1

        coEvery { imagesService.load(numberOfImages) } returns List(numberOfImages) { mockedImageEntity }

        imagesRepository.addImage()

        testFetchImages(numberOfImages)
    }

    @Test
    fun testReloadImages() = runBlocking {
        val numberOfImagesReturnedFromRequest = Constant.NUMBER_OF_ITEMS_PER_PAGE
        val fullNumberOfImages = Constant.NUMBER_OF_ITEMS_PER_PAGE * Constant.NUMBER_OF_PAGES

        coEvery { imagesService.load(numberOfImagesReturnedFromRequest) } returns List(numberOfImagesReturnedFromRequest) { mockedImageEntity }

        imagesRepository.reloadAllImages()

        testFetchImages(fullNumberOfImages)
    }

    private suspend fun testFetchImages(numberOfImages: Int) {
        delay(20)
        val images = imagesRepository.fetchImagesAsFlow().first()

        assertEquals(numberOfImages, images.size)
        assertEquals(mockedImageModel, images.first())
    }

    private fun createTestSqlDriver() = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        Database.Schema.create(this)
    }
}
