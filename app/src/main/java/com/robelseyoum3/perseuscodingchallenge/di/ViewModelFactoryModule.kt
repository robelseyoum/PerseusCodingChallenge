package com.robelseyoum3.perseuscodingchallenge.di

import androidx.lifecycle.ViewModelProvider
import com.robelseyoum3.perseuscodingchallenge.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}