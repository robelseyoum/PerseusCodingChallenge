package com.robelseyoum3.perseuscodingchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.OpenNotify
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.Request
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.Response
import com.robelseyoum3.perseuscodingchallenge.data.repository.SpaceRepository
import com.robelseyoum3.perseuscodingchallenge.ui.spacenasaimage.SpaceViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.net.UnknownHostException


@RunWith(BlockJUnit4ClassRunner::class)
class SpaceViewModelTest {


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: SpaceRepository

    lateinit var spaceViewModel: SpaceViewModel



    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        spaceViewModel = SpaceViewModel(repository)
    }

    /**
     * Test cases for getting overhead times scenario
     */
    private val LATITUDE = "37.4219983"
    private val LONGITUDE = "-122.084"
    private val DATE = "2020-06-18"
    //Response(343, 1592773252)
    //Request(100, 1592769710,45.0 ,-122.3, 5)

    @Test
    fun fetchISSOverheadLocation_successOpenNotifyResponseList() {
        val openNotifyResponse = OpenNotify( "success",
            Request(100, 1592769710,45.0 ,-122.3, 5),
            mutableListOf(Response(343, 1592773252)) )
        val emptyError = null

        every { repository.getFromSpaceStation(LATITUDE, LONGITUDE) } returns (Single.just(openNotifyResponse))

        spaceViewModel.getISSOverheadLocation(LATITUDE, LONGITUDE)

        assertEquals(SpaceViewModel.LoadingState.SUCCESS(openNotifyResponse.response), spaceViewModel.loadingState.value)

    }

    @Test
    fun fetchISSOverheadLocation_successEmptyOpenNotifyResponseList() {
        val openNotifyResponse = OpenNotify( "success",
            Request(100, 1592769710,45.0 ,-122.3, 5),
            mutableListOf() )

        val emptyError = "No Overhead Space Location"
        every { repository.getFromSpaceStation(LATITUDE, LONGITUDE) } returns (Single.just(openNotifyResponse))

        spaceViewModel.getISSOverheadLocation(LATITUDE, LONGITUDE)

        assertEquals(SpaceViewModel.LoadingState.ERROR(emptyError), spaceViewModel.loadingState.value)

    }


    @Test
    fun fetchISSOverheadLocation_networkError() {
        val noNetworkError = "No Network"

        every{  repository.getFromSpaceStation(LATITUDE, LONGITUDE) } returns
                (Single.error(UnknownHostException(noNetworkError)))

        spaceViewModel.getISSOverheadLocation(LATITUDE, LONGITUDE)

        assertEquals(SpaceViewModel.LoadingState.ERROR(noNetworkError), spaceViewModel.loadingState.value)

    }


    @Test
    fun fetchISSOverheadLocation_otherError() {
        val otherError = "Other Error"

        every{  repository.getFromSpaceStation(LATITUDE, LONGITUDE) } returns
                Single.error(RuntimeException(otherError))

        spaceViewModel.getISSOverheadLocation(LATITUDE, LONGITUDE)

        assertEquals(SpaceViewModel.LoadingState.ERROR(otherError), spaceViewModel.loadingState.value)

    }


}
