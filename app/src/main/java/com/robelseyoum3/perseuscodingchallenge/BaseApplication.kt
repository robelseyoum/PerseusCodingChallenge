package com.robelseyoum3.perseuscodingchallenge

import com.robelseyoum3.perseuscodingchallenge.di.applevel.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class BaseApplication : DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}