package com.robelseyoum3.perseuscodingchallenge.di.applevel

import com.robelseyoum3.perseuscodingchallenge.di.nasaimage.NasaFragmentBuildersModule
import com.robelseyoum3.perseuscodingchallenge.di.nasaimage.NasaImageViewModelModule
import com.robelseyoum3.perseuscodingchallenge.di.space.SpaceFragmentBuildersModule
import com.robelseyoum3.perseuscodingchallenge.di.space.SpaceViewModelModule
import com.robelseyoum3.perseuscodingchallenge.ui.space.SpaceActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {


    @ContributesAndroidInjector(
        modules = [SpaceFragmentBuildersModule::class, SpaceViewModelModule::class,
            NasaFragmentBuildersModule::class, NasaImageViewModelModule::class]
    )
    abstract fun contributeSpaceActivity(): SpaceActivity


}