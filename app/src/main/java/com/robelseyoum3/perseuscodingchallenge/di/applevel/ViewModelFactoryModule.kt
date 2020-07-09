package com.robelseyoum3.perseuscodingchallenge.di.applevel

import androidx.lifecycle.ViewModelProvider
import com.robelseyoum3.perseuscodingchallenge.ui.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    /**
     * we can also use @Provides
     *
     */
//    companion object{
//        @Provides
//        fun bindViewModelFactory( factory: ViewModelProvider.Factory) :ViewModelProvider.Factory{
//            return factory
//        }
//    }
}