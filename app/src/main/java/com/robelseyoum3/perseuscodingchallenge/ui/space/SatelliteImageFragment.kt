package com.robelseyoum3.perseuscodingchallenge.ui.space

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
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

class SatelliteImageFragment : BaseSpaceFragment() {

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
        var basUrl = "$BASE_URL_NASA$longitude$LAT$latitude$DATE_PLACEHOLDER$date$API_KEY_PLACEHOLDER$API_KEY"
        Log.d(TAG, "SatelliteImageFragment_baseUrl: $basUrl ")

        viewModel.getSatelliteImage(basUrl)

        viewModel.nasaImageResults.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> displayProgressbar()
                is Resource.Success -> displayImage(it.data)
                is Resource.Error -> displayMessageContainer(it.message)
                else -> displayMessageContainer("Unknown Error")
            }
        })

    }

    private fun displayImage(data: EarthPicture?) {
        Log.d(TAG, "SatelliteImageFragment_id: ${data?.id} ")
//        view?.let {
//            Glide.with(it.context)
//                .load(data?.url)
//                .into(earth_image)
//        }
        Picasso.get().load(data!!.url).into(earth_image)

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