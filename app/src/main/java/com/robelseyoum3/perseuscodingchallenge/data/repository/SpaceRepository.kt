package com.robelseyoum3.perseuscodingchallenge.data.repository

import android.util.AndroidException
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.OpenNotify
import com.robelseyoum3.perseuscodingchallenge.data.model.nasaimage.EarthPicture
import com.robelseyoum3.perseuscodingchallenge.data.remote.WebServices
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SpaceRepository(private val webServices: WebServices) {

    fun getFromSpaceStation(latitude: String, longitude: String): Single<OpenNotify> {
        return webServices
            .getOverheadLocation(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNasaLocationImage(url: String): Single<EarthPicture> {
        return webServices
            .getNasaLocationImage(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
