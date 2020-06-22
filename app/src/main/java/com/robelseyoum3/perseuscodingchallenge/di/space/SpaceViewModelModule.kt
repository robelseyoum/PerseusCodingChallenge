package com.robelseyoum3.perseuscodingchallenge.di.space

import androidx.lifecycle.ViewModel
import com.robelseyoum3.perseuscodingchallenge.di.applevel.ViewModelKey
import com.robelseyoum3.perseuscodingchallenge.ui.space.SpaceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SpaceViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SpaceViewModel::class)
    abstract fun bindAuthViewModel(spaceViewModel: SpaceViewModel): ViewModel
}