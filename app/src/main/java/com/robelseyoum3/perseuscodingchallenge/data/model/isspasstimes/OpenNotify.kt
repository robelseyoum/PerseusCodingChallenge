package com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OpenNotify(
    @SerializedName("message")
    val message: String,
    @SerializedName("request")
    val request: Request,
    @SerializedName("response")
    val response: List<Response>
): Parcelable

@Parcelize
data class Request(
    @SerializedName("altitude")
    val altitude: Int,
    @SerializedName("datetime")
    val datetime: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("passes")
    val passes: Int
): Parcelable


@Parcelize
data class Response(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("risetime")
    val risetime: Int
): Parcelable