package com.robelseyoum3.perseuscodingchallenge.di.space

import com.robelseyoum3.perseuscodingchallenge.ui.nasaimage.SatelliteImageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SpaceFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeSatelliteImageFragment(): SatelliteImageFragment

}