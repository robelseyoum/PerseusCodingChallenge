package com.robelseyoum3.perseuscodingchallenge.ui.nasaImage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robelseyoum3.perseuscodingchallenge.data.model.nasaimage.EarthPicture
import com.robelseyoum3.perseuscodingchallenge.data.repository.SpaceRepository
import com.robelseyoum3.perseuscodingchallenge.ui.space.SpaceViewModel
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import javax.inject.Inject

class NasaImageViewModel @Inject constructor(private val spaceRepository: SpaceRepository): ViewModel(){


        fun  getSatelliteImage(url: String) {

            loadingState.value = LoadingState.LOADING

        disposable.add(
             spaceRepository.getNasaLocationImage(url)
                 .subscribe({
                     if(it.url.isEmpty()){
                         loadingState.value = LoadingState.ERROR("No Image found from NASA")

                     } else{
                         loadingState.value  = LoadingState.SUCCESS(it)

                     }
                 }, {
                     it.printStackTrace()
                     val noNetworkError = "No Network"
                     loadingState.value = LoadingState.ERROR(
                         when(it){
                             is UnknownHostException -> noNetworkError
                             else -> it.localizedMessage
                         }
                     )
                 })
        )
    }


    private val disposable = CompositeDisposable()

    val loadingState = MutableLiveData<LoadingState>()

    sealed class LoadingState {
        object LOADING : LoadingState()
        data class SUCCESS(val response: EarthPicture) : LoadingState()
        data class ERROR(val message: String) : LoadingState()
    }
}