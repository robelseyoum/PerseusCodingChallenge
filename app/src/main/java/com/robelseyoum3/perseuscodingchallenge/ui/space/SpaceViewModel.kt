package com.robelseyoum3.perseuscodingchallenge.ui.space

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.Response
import com.robelseyoum3.perseuscodingchallenge.data.repository.SpaceRepository
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

class SpaceViewModel @Inject constructor(private val spaceRepository: SpaceRepository): ViewModel(){

    fun getISSOverheadLocation(latitude: String, longitude: String) {

        loadingState.value = LoadingState.LOADING

        disposable.add(

        spaceRepository.getFromSpaceStation(latitude, longitude)
            .subscribe({

                lastFetchedTime = Date()

                if (it.response.isEmpty()) {
                    loadingState.value = LoadingState.ERROR("No Overhead Space Location")
                    } else {
                    loadingState.value  = LoadingState.SUCCESS(it.response.toMutableList())
                }
            },
            {
                lastFetchedTime = Date()
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


    sealed class LoadingState {
        object LOADING : LoadingState()
        data class SUCCESS(val response: List<Response>) : LoadingState()
        data class ERROR(val message: String) : LoadingState()
    }

    val loadingState = MutableLiveData<LoadingState>()

    private val disposable = CompositeDisposable()

    var lastFetchedTime: Date? = null

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
