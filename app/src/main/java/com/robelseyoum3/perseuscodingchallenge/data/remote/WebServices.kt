package com.robelseyoum3.perseuscodingchallenge.data.remote

import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.OpenNotify
import com.robelseyoum3.perseuscodingchallenge.data.model.nasaimage.EarthPicture
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.ENDPOINT
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WebServices {

    @GET(ENDPOINT)
    fun getOverheadLocation(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?
    ): Single<OpenNotify>


//    @GET
//    fun getNasaLocationImage(
//        @Query("lat") lat: String?,
//        @Query("lon") lon: String?,
//        @Query("apikey") apiKey: String?
//    ): Single<EarthPicture>

    @GET
    fun getNasaLocationImage(
        @Url url: String
    ): Single<EarthPicture>

}