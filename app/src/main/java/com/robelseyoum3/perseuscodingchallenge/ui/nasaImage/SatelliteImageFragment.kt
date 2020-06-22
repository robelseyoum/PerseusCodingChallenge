package com.robelseyoum3.perseuscodingchallenge.ui.nasaImage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.robelseyoum3.perseuscodingchallenge.R
import com.robelseyoum3.perseuscodingchallenge.data.model.nasaimage.EarthPicture
import com.robelseyoum3.perseuscodingchallenge.ui.space.IssPassFragment
import com.robelseyoum3.perseuscodingchallenge.ui.viewmodel.ViewModelProviderFactory
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.API_KEY
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.API_KEY_PLACEHOLDER
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.BASE_URL_NASA
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.DATE_PLACEHOLDER
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.LAT
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_isspasstimes.*

import kotlinx.android.synthetic.main.fragment_satellite_image.*
import javax.inject.Inject

class SatelliteImageFragment : DaggerFragment() {

    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var currentDate: String

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var nasaImageViewModel: NasaImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_satellite_image, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nasaImageViewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(NasaImageViewModel::class.java)
        }?: throw Exception("Invalid Activity")

        latitude = arguments?.getString(IssPassFragment.LATITUDE1).orEmpty()
        longitude = arguments?.getString(IssPassFragment.LONGITUDE1).orEmpty()
        currentDate = arguments?.getString(IssPassFragment.CURRENT_DATE).orEmpty()

        subscribes(latitude, longitude, currentDate)
    }

    private fun subscribes(latitude: String, longitude: String, date: String) {
        val sampleDate = "2020-06-20" //use this sample date if the API didn't response the IMAGE with a current date
        val basUrl = "$BASE_URL_NASA$longitude$LAT$latitude$DATE_PLACEHOLDER$sampleDate$API_KEY_PLACEHOLDER$API_KEY"

        nasaImageViewModel.getSatelliteImage(basUrl)

        nasaImageViewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when(it){
                is NasaImageViewModel.LoadingState.LOADING -> displayProgressbar()
                is NasaImageViewModel.LoadingState.SUCCESS -> displayImage(it.response)
                is NasaImageViewModel.LoadingState.ERROR -> displayMessageContainer(it.message)
                else -> displayMessageContainer("Unknown Error")
            }
        })

        btnRetrySat.setOnClickListener {
            nasaImageViewModel.getSatelliteImage(basUrl)
        }

    }

    private fun displayImage(data: EarthPicture?) {
        llMessageContainer_img.visibility = View.GONE
        Picasso.get().load(data!!.url).into(earth_image)
        progress_bar_img.visibility = View.GONE
    }


    private fun displayProgressbar() {
        progress_bar_img.visibility = View.VISIBLE
        llMessageContainer_img.visibility = View.GONE
    }


    private fun displayMessageContainer(message: String?) {
        llMessageContainer_img.visibility = View.VISIBLE
        progress_bar_img.visibility = View.GONE
        tvMessage_img.text = message
    }

}