package com.robelseyoum3.perseuscodingchallenge.ui.space

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.OpenNotify
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.Response
import com.robelseyoum3.perseuscodingchallenge.data.repository.SpaceRepository
import com.robelseyoum3.perseuscodingchallenge.utils.Constant.Companion.LATITUDE
import com.robelseyoum3.perseuscodingchallenge.utils.Constant.Companion.LONGITUDE
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

class SpaceViewModel @Inject constructor(private val spaceRepository: SpaceRepository): ViewModel(){

    val TAG: String = "AppDebug"

    fun getISSOverheadLocation(latitude: String, longitude: String) {

        loadingState.value = LoadingState.LOADING

        disposable.add(

        spaceRepository.getFromSpaceStation(latitude, longitude)
            .subscribe({
                lastFetchedTime = Date()

                if (it.response.isEmpty()) {
                        errorMessage.value = "No Overhead Space Location"
                        loadingState.value = LoadingState.ERROR
                    } else {
                    results.value = it.response.toMutableList()
                    loadingState.value = LoadingState.SUCCESS
                }
            },
            {
                lastFetchedTime = Date()
                it.printStackTrace()

                when(it){
                    is UnknownHostException -> errorMessage.value = "No Network"
                    else -> errorMessage.value = it.localizedMessage
                }
                loadingState.value = LoadingState.ERROR
            })
        )
    }


    enum class LoadingState {
        LOADING,
        SUCCESS,
        ERROR
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private val disposable = CompositeDisposable()

    var results: MutableLiveData<MutableList<Response>> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val loadingState = MutableLiveData<LoadingState>()

    var lastFetchedTime: Date? = null
}