package com.robelseyoum3.perseuscodingchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.robelseyoum3.perseuscodingchallenge.data.model.nasaimage.EarthPicture
import com.robelseyoum3.perseuscodingchallenge.data.model.nasaimage.Resource
import com.robelseyoum3.perseuscodingchallenge.data.repository.SpaceRepository
import com.robelseyoum3.perseuscodingchallenge.ui.nasaimage.NasaImageViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.net.UnknownHostException

@RunWith(BlockJUnit4ClassRunner::class)
class NasaImageViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: SpaceRepository

    lateinit var nasaImageViewModel: NasaImageViewModel


    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        nasaImageViewModel = NasaImageViewModel(repository)
    }


    /**
     * Test cases for EarthPicture scenario
     */

    @Test
    fun fetchSatelliteImage_successEarthPicture() {
        var earthImage = EarthPicture("data", "id", Resource("dataset", "planet"),
            "service_version", "url")

        every { repository.getNasaLocationImage(earthImage.url) } returns (Single.just(earthImage))

        nasaImageViewModel.getSatelliteImage(earthImage.url)

        Assert.assertEquals(NasaImageViewModel.LoadingState.SUCCESS(earthImage), nasaImageViewModel.loadingState.value)

    }

    @Test
    fun fetchSatelliteImage_successEmptyEarthPicture() {

        var earthImage = EarthPicture("data", "id", Resource("dataset", "planet"),
            "service_version", "")

        val emptyError = "No Image found from NASA"

        every { repository.getNasaLocationImage(earthImage.url) } returns (Single.just(earthImage))

        nasaImageViewModel.getSatelliteImage(earthImage.url)

        Assert.assertEquals(NasaImageViewModel.LoadingState.ERROR(emptyError), nasaImageViewModel.loadingState.value)

    }


    @Test
    fun fetchSatelliteImage_networkError() {
        var earthImage = EarthPicture("data", "id", Resource("dataset", "planet"),
            "service_version", "url")

        val noNetworkError = "No Network"

        every { repository.getNasaLocationImage(earthImage.url) } returns
                Single.error(UnknownHostException(noNetworkError))

        nasaImageViewModel.getSatelliteImage(earthImage.url)

        Assert.assertEquals(NasaImageViewModel.LoadingState.ERROR(noNetworkError), nasaImageViewModel.loadingState.value)
    }

    @Test
    fun fetchSatelliteImage_otherError() {
        var earthImage = EarthPicture("data", "id", Resource("dataset", "planet"),
            "service_version", "url")

        val otherError = "Other Error"

        every { repository.getNasaLocationImage(earthImage.url) } returns
                Single.error(RuntimeException(otherError))

        nasaImageViewModel.getSatelliteImage(earthImage.url)

        Assert.assertEquals(NasaImageViewModel.LoadingState.ERROR(otherError), nasaImageViewModel.loadingState.value)

    }
}