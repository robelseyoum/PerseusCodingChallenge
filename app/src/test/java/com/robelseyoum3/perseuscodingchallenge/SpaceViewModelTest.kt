package com.robelseyoum3.perseuscodingchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.robelseyoum3.perseuscodingchallenge.data.repository.SpaceRepository
import com.robelseyoum3.perseuscodingchallenge.ui.space.SpaceViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
class SpaceViewModelTest {


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: SpaceRepository

    lateinit var spaceViewModel: SpaceViewModel

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        spaceViewModel = SpaceViewModel(repository)
    }


    /**
     * Test cases for getting overhead times scenario
     */

    @Test
    fun fetchISSOverheadLocation_successOpenNotifyResponseList() {

    }

    @Test
    fun fetchISSOverheadLocation_successEmptyOpenNotifyResponseList() {

    }


    @Test
    fun fetchISSOverheadLocation_networkError() {

    }


    @Test
    fun fetchISSOverheadLocation_otherError() {

    }

    /**
     * Test cases for EarthPicture scenario
     */

    @Test
    fun fetchSatelliteImage_successEarthPicture() {

    }

    @Test
    fun fetchSatelliteImage_successEmptyEarthPicture() {

    }


    @Test
    fun fetchSatelliteImage_networkError() {

    }

    @Test
    fun fetchSatelliteImage_otherError() {

    }


}
