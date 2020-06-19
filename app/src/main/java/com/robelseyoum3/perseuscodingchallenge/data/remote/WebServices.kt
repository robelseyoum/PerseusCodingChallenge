package com.robelseyoum3.perseuscodingchallenge.data.remote

import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.OpenNotify
import com.robelseyoum3.perseuscodingchallenge.utils.Constant.Companion.ENDPOINT
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET(ENDPOINT)
    fun getOverheadLocation(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?
    ): Single<OpenNotify>

}