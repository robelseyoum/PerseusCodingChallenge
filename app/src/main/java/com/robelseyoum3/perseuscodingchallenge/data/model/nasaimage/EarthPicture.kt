package com.robelseyoum3.perseuscodingchallenge.data.model.nasaimage


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EarthPicture(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("resource")
    val resource: Resource,
    @SerializedName("service_version")
    val serviceVersion: String,
    @SerializedName("url")
    val url: String
): Parcelable

@Parcelize
data class Resource(
    @SerializedName("dataset")
    val dataset: String,
    @SerializedName("planet")
    val planet: String
): Parcelable