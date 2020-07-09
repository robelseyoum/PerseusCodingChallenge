package com.robelseyoum3.perseuscodingchallenge.utils

class Constants {

    companion object {

        const val BASE_URL_OPEN_NOTIFY = "http://api.open-notify.org/"
        const val ENDPOINT = "iss-pass.json"


        //image from NASA
        const val BASE_URL_NASA = "https://api.nasa.gov/planetary/earth/assets?lon="
        const val LAT = "&lat="
        const val DATE_PLACEHOLDER = "&date="
        const val API_KEY_PLACEHOLDER  = "&api_key="
        const val API_KEY ="3ZdeXOdyxNX4zTa2GqpQpRahBT7i4cyLGnj3ibhU"


    }
}


//class Resource<T>(val status: Status, val data: T?, message: String) {
//    val message: String?
//
//    enum class Status {
//        SUCCESS, ERROR, LOADING
//    }
//
//    companion object {
//        fun <T> success(data: T, message: String): Resource<T> {
//            return Resource(Status.SUCCESS, data, message)
//        }
//
//        fun <T> error(data: T?, msg: String): Resource<T?> {
//            return Resource(Status.ERROR, data, msg)
//        }
//
//        fun <T> loading(data: T?): Resource<T?> {
//            return Resource(Status.LOADING, data, null)
//        }
//    }
//
//    init {
//        this.message = message
//    }
//}