package com.robelseyoum3.perseuscodingchallenge.ui.spacenasaimage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.Response
import com.robelseyoum3.perseuscodingchallenge.data.model.nasaimage.EarthPicture
import com.robelseyoum3.perseuscodingchallenge.data.repository.SpaceRepository
import com.robelseyoum3.perseuscodingchallenge.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

class SpaceViewModel @Inject constructor(private val spaceRepository: SpaceRepository): ViewModel(){

    fun getISSOverheadLocation(latitude: String, longitude: String) {

        notifyResults.value =  Resource.Loading(null)

        disposable.add(

        spaceRepository.getFromSpaceStation(latitude, longitude)
            .subscribe({

                lastFetchedTime = Date()

                if(it.response.isEmpty()){
                    notifyResults.value= Resource.Error("No Overhead Space Location")
                }else {
                    notifyResults.value = Resource.Success(it.response.toMutableList())
                }

            },
            {
                lastFetchedTime = Date()
                it.printStackTrace()
                val noNetworkError = "No Network"
                notifyResults.value = Resource.Error(
                    when(it){
                        is UnknownHostException -> noNetworkError
                        else -> it.localizedMessage
                    },
                    null
                )
            })

        )

    }

    fun  getSatelliteImage(url: String) {

        notifyNasaImageResults.value = Resource.Loading(null)

        disposable.add(
            spaceRepository.getNasaLocationImage(url)
                .subscribe({
                    lastFetchedTime = Date()
                    if(it.url.isEmpty()){
                        notifyNasaImageResults.value = Resource.Error("No Image found from NASA")
                    } else{
                        notifyNasaImageResults.value = Resource.Success(it)
                    }
                },
                {
                    it.printStackTrace()
                    val noNetworkError = "No Network"
                    notifyNasaImageResults.value = Resource.Error(
                        when(it){
                            is UnknownHostException -> noNetworkError
                            else -> it.localizedMessage
                        },
                        null
                    )
                })
        )
    }


    var notifyResults: MutableLiveData<Resource<MutableList<Response>>> = MutableLiveData()
    var notifyNasaImageResults: MutableLiveData<Resource<EarthPicture>> = MutableLiveData()

    private val disposable = CompositeDisposable()

    var lastFetchedTime: Date? = null

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
