package com.robelseyoum3.perseuscodingchallenge.di.nasaimage

import com.robelseyoum3.perseuscodingchallenge.ui.space.IssPassFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NasaFragmentBuildersModule {
    
    @ContributesAndroidInjector()
    abstract fun contributeIssPassFragment(): IssPassFragment
}