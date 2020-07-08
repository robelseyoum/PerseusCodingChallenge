package com.robelseyoum3.perseuscodingchallenge.di.spaceandnasa

import com.robelseyoum3.perseuscodingchallenge.ui.spacenasaimage.SatelliteImageFragment
import com.robelseyoum3.perseuscodingchallenge.ui.spacenasaimage.IssPassFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SpaceFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeIssPassFragment(): IssPassFragment

    @ContributesAndroidInjector()
    abstract fun contributeSatelliteImageFragment(): SatelliteImageFragment

}