package com.robelseyoum3.perseuscodingchallenge.ui.space

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

                if (it.response.isEmpty()) {
                  notifyResults.value =   Resource.Error("No Overhead Space Location", null)
                    } else {
                    notifyResults.value = Resource.Success(it.response.toMutableList())
                }
            },
            {
                lastFetchedTime = Date()
                it.printStackTrace()
                notifyResults.value = Resource.Error(
                    when(it){
                        is UnknownHostException -> "No Network"
                        else -> it.localizedMessage
                    },
                    null
                )
            })
        )
    }


    fun  getSatelliteImage(url: String){

        nasaImageResults.value = Resource.Loading(null)

        disposable.add(
             spaceRepository.getNasaLocationImage(url)
                 .subscribe({
                  lastFetchedTime = Date()
                     if(it.url.isEmpty()){
                         nasaImageResults.value = Resource.Error("No Image found from NASA", null)
                     } else{
                         nasaImageResults.value = Resource.Success(it)
                     }
                 }, {
                     lastFetchedTime = Date()
                     it.printStackTrace()
                     nasaImageResults.value = Resource.Error(
                         when(it){
                             is UnknownHostException -> "No Network"
                             else -> it.localizedMessage
                         },
                         null
                     )
                 })
        )
    }


    private val disposable = CompositeDisposable()

    var notifyResults: MutableLiveData<Resource<MutableList<Response>>> = MutableLiveData()

    var nasaImageResults: MutableLiveData<Resource<EarthPicture>> = MutableLiveData()


    var lastFetchedTime: Date? = null

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
