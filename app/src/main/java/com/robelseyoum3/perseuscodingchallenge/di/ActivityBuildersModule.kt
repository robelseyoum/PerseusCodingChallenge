package com.robelseyoum3.perseuscodingchallenge.di

import com.robelseyoum3.perseuscodingchallenge.ui.space.SpaceActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {


    @ContributesAndroidInjector(
        modules = [SpaceFragmentBuildersModule::class, SpaceViewModelModule::class]
    )
    abstract fun contributeSpaceActivity(): SpaceActivity


}