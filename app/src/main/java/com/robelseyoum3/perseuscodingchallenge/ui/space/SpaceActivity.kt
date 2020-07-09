package com.robelseyoum3.perseuscodingchallenge.ui.space

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.robelseyoum3.perseuscodingchallenge.R
import com.robelseyoum3.perseuscodingchallenge.ui.BaseActivity
import com.robelseyoum3.perseuscodingchallenge.ui.viewmodel.ViewModelProviderFactory
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.util.*

import javax.inject.Inject

class SpaceActivity : BaseActivity() {

    private val PERMISSION_ID = 42
    //Fused Location Provider API to get users current position.
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentDate: String

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: SpaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space)
        viewModel = ViewModelProvider(this, providerFactory).get(SpaceViewModel::class.java)

        /**
         * The fused location provider retrieves the device's last known location.
           It provides simple and easy to use APIs.
           Provides high accuracy over other options.
           Utilizes low power by choosing the most efficient way to access the location.
         */
        mFusedLocationClient  = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        currentDate()
    }

    private fun currentDate() {
        currentDate = SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).format(Date())
        Log.d(TAG, "Dates : $currentDate")
    }

    /**
     * This is code is inspired from this tutorial
     * https://www.androdocs.com/tutorials/getting-current-location-latitude-longitude-in-android-using-kotlin.html
     *
     *
     *  which will use to API and return the last recorder location information of the device.
     *  Also this method will check first if our permission is granted or not and if the location setting is turned on.
     */
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        //To avoid these rare cases when the location == null,
                        // we called a new method requestNewLocationData() which will record the location information in runtime.
                        requestNewLocationData()
                    } else {
                        val bundle = Bundle()
                        val latitude = location.latitude.toString()
                        Log.d(TAG, "Latitude: $latitude")
                        val longitude = location.longitude.toString()
                        Log.d(TAG, "Longitude: $longitude")
                        passCoordination(latitude, longitude) //here the two coordinator is passed to
                    }
                }
            } else {
                 Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    //here the two coordination location number is passed to Fragment
    private fun passCoordination(latitude: String, longitude: String)  {
        currentDate = SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).format(Date())
        val coordinateNumber = IssPassFragment.newInstance(latitude, longitude, currentDate)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, coordinateNumber, "IssPassFragment")
            .commit()
    }

    /**
     * To avoid these rare cases when the location == null, we called a new method requestNewLocationData()
     * which will record the location information in runtime.
     * This method will make a new location request with highest accuracy using LocationRequest.PRIORITY_HIGH_ACCURACY.
     */
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    /**
     * This callback used when an update receives it'll call a callBack method named mLocationCallback
     * So when we get the location update, we set the latitude and longitude values in our TextViews.
     */
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            //here we can use mLastLocation to get updated new location coordinator
        }
    }

    /**
     * This method will tell us whether or not the user grant us to access ACCESS_COARSE_LOCATION and ACCESS_FINE_LOCATION.
     */
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    /**
     * This method will request our necessary permissions to the user if they are not already granted.
     */
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    /**
     * This method is called when a user Allow or Deny our requested permissions. So it will help us to move forward if the permissions are granted.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
            }
        }
    }

    /**
     * This will check if the user has turned on location from the setting, Cause user may grant the app to user location
     * but if the location setting is off then it'll be of no use.
     */
    private fun isLocationEnabled(): Boolean {

        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

}
