package com.robelseyoum3.perseuscodingchallenge.di

import com.robelseyoum3.perseuscodingchallenge.ui.space.IssPassFragment
import com.robelseyoum3.perseuscodingchallenge.ui.nasaImage.SatelliteImageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SpaceFragmentBuildersModule {


    @ContributesAndroidInjector()
    abstract fun contributeSatelliteImageFragment(): SatelliteImageFragment

    @ContributesAndroidInjector()
    abstract fun contributeIssPassFragment(): IssPassFragment

}