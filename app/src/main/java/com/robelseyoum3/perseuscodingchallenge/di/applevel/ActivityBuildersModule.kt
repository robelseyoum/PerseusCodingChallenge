package com.robelseyoum3.perseuscodingchallenge.di.applevel

import com.robelseyoum3.perseuscodingchallenge.di.spaceandnasa.SpaceFragmentBuildersModule
import com.robelseyoum3.perseuscodingchallenge.di.spaceandnasa.SpaceViewModelModule
import com.robelseyoum3.perseuscodingchallenge.ui.spacenasaimage.SpaceActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {


    @ContributesAndroidInjector(
        modules = [SpaceFragmentBuildersModule::class, SpaceViewModelModule::class]
    )
    abstract fun contributeSpaceActivity(): SpaceActivity


}