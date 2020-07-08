package com.robelseyoum3.perseuscodingchallenge.di.nasaimage

import androidx.lifecycle.ViewModel
import com.robelseyoum3.perseuscodingchallenge.di.applevel.ViewModelKey
import com.robelseyoum3.perseuscodingchallenge.ui.nasaimage.NasaImageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NasaImageViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NasaImageViewModel::class)
    abstract fun bindAuthViewModel(nasaImageViewModel: NasaImageViewModel): ViewModel
}

