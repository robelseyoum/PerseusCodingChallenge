package com.robelseyoum3.perseuscodingchallenge.ui.spacenasaimage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.robelseyoum3.perseuscodingchallenge.R
import com.robelseyoum3.perseuscodingchallenge.data.model.nasaimage.EarthPicture
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.API_KEY
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.API_KEY_PLACEHOLDER
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.BASE_URL_NASA
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.DATE_PLACEHOLDER
import com.robelseyoum3.perseuscodingchallenge.utils.Constants.Companion.LAT
import com.robelseyoum3.perseuscodingchallenge.utils.Resource
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_satellite_image.*

class SatelliteImageFragment : BaseSpaceImageFragment() {

    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var currentDate: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_satellite_image, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "SatelliteImageFragment: ${viewModel.hashCode()} ")

        latitude = arguments?.getString(IssPassFragment.LATITUDE1).orEmpty()
        longitude = arguments?.getString(IssPassFragment.LONGITUDE1).orEmpty()
        currentDate = arguments?.getString(IssPassFragment.CURRENT_DATE).orEmpty()

        subscribes(latitude, longitude, currentDate)
    }

    private fun subscribes(latitude: String, longitude: String, date: String) {
        val sampleDate = "2020-06-20" //use this sample date if the API didn't response the IMAGE with a current date
        val basUrl = "$BASE_URL_NASA$longitude$LAT$latitude$DATE_PLACEHOLDER$sampleDate$API_KEY_PLACEHOLDER$API_KEY"

        viewModel.getSatelliteImage(basUrl)

        viewModel.notifyNasaImageResults.observe(viewLifecycleOwner, Observer { data ->
            when(data){
                is Resource.Loading -> displayProgressbar()
                is Resource.Success -> displayImage(data.data)
                is Resource.Error -> displayMessageContainer(data.message)
                else -> displayMessageContainer("Unknown Error")
            }
        })

        btnRetrySat.setOnClickListener {
            viewModel.getSatelliteImage(basUrl)
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