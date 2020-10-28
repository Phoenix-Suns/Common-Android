package com.nghiatl.common.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat

private const val MY_PERMISSION_ACCESS_COURSE_LOCATION = 99
private const val DEFAULT_MIN_METER_UPDATE_DISTANCE: Long = 10 // 10 meters
private const val DEFAULT_MIN_MILLISECOND_UPDATE_TIME: Long = 1 // 1/1000s

class LocationService(
    private val context: androidx.fragment.app.FragmentActivity,
    private val updateRealtime: Boolean = false,
    var listener: Listener? = null
) {
    private val LASTEST_LOCATION_NAME: String = "LASTEST_LOCATION_NAME"
    private val KEY_LATITUDE: String = "KEY_LATITUDE"
    private val KEY_LONGITUDE: String = "KEY_LONGITUDE"
    private val TAG: String by lazy { this.javaClass.simpleName }

    var mlocation: Location? = null
    var canGetLocation = false
    var minMeterUpdateDistance: Long = DEFAULT_MIN_METER_UPDATE_DISTANCE
    var minMillisecondUpdateTime: Long = DEFAULT_MIN_MILLISECOND_UPDATE_TIME

    // V1
    private var locationManager: LocationManager? = null
    private val locationManagerListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            updateLocation(location, true)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    // V2
    /*private var locationProviderClient: FusedLocationProviderClient? = null
    private val locationProviderListener = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.let { locationResult ->
                updateLocation(locationResult.lastLocation, true)
            }
        }
    }
    private var locationRequest: LocationRequest? = null*/


    init {
        getLocationBySystemService()    // todo remove
        /*Dexter.withActivity(context)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            getLocationBySystemService()
                            Timber.e("$TAG : onPermissionsChecked allow")
                        } else {
                            Timber.e("$TAG : onPermissionsChecked not allow")
                            showRequestPermissionDialog()
                            updateLocation(location, false)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    token?.continuePermissionRequest()  //request location again
                }
            })
            .withErrorListener {
                Timber.e("$TAG : withErrorListener")
            }.check()*/
    }

    /*fun showRequestPermissionDialog() {
        AppDialog.showMessageDialog(
            context.supportFragmentManager, false,
            context.getString(R.string.permission_request_location_title),
            context.getString(R.string.permission_request_location_message),
            context.getString(R.string.permission_settings),
            { view, dialog ->
                // goto setting
                openSettings()
                dialog.dismiss()
            },
            context.getString(R.string.common_cancel),
            { view, dialog -> dialog.dismiss() }
        )
    }*/

    // navigating user to app settings
    /*private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivityForResult(intent, 101)
    }*/

    @SuppressLint("MissingPermission")
    private fun getLocationBySystemService() {
        var location: Location? = null
        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager?.let { locationManager ->
                if (validateLocationPermission()) {
                    //val gpsProvider = if (isNetworkEnabled) LocationManager.NETWORK_PROVIDER else LocationManager.GPS_PROVIDER

                    // check all providers can get GPS
                    for (gpsProvider in locationManager.allProviders) {
                        location = locationManager.getLastKnownLocation(gpsProvider)
                        if (location != null) {
                            updateLocation(location!!, true)

                            // Auto update when gps change
                            if (updateRealtime) {
                                locationManager.requestLocationUpdates(
                                    gpsProvider,
                                    minMillisecondUpdateTime,
                                    minMeterUpdateDistance.toFloat(),
                                    locationManagerListener)
                            }
                            break
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // call get gps by method 2
        if (this.mlocation == null) {
            //getLocationByLocationService()
            updateLocation(canGetLocation = false)  // todo remove
        }
    }

    /*@SuppressLint("MissingPermission")
    fun getLocationByLocationService() {
        if (!validateLocationPermission()) return

        locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationProviderClient?.lastLocation?.addOnSuccessListener { location: Location? ->
            if (location != null) {
                updateLocation(location, true)

                // Auto update when gps change
                if (updateRealtime) {
                    locationRequest = LocationRequest.create()
                    locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    locationRequest?.interval = minMillisecondUpdateTime
                    locationProviderClient?.requestLocationUpdates(LocationRequest(), locationProviderListener, Looper.myLooper())
                }
            } else {
                updateLocation(canGetLocation = false)
            }
        }?.addOnFailureListener {
            *//*when ((it as ApiException).statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {

                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                }
            }*//*
            updateLocation(canGetLocation = false)
        }
    }*/

    private fun updateLocation(location: Location = Location(TAG), canGetLocation: Boolean) {
        this.canGetLocation = canGetLocation
        this.mlocation = location
        saveLatestLocation(location)
        listener?.onLocationChanged(location)
        Log.d(TAG, "latitude: ${location.latitude}, longitude: ${location.longitude}")
    }

    val location: Location
        get() {
            return if (mlocation != null) {
                mlocation!!
            } else {
                Location(TAG).apply {
                    latitude = getLatestLatitude()
                    longitude = getLatestLongitude()
                }
            }
        }

    val latitude: Double
        get() = this.mlocation?.latitude ?: getLatestLatitude()

    val longitude: Double
        get() = this.mlocation?.longitude ?: getLatestLongitude()

    @SuppressLint("MissingPermission")
    fun stopUsingGPS() {
        if (!validateLocationPermission()) return

        locationManager?.removeUpdates(locationManagerListener)
        //locationProviderClient?.removeLocationUpdates(locationProviderListener)
    }

    private fun validateLocationPermission(): Boolean {
        //Check + Request Permission
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSION_ACCESS_COURSE_LOCATION)
            return false
        }
        return true
    }

    private fun saveLatestLocation(location: Location) {
        context.getSharedPreferences(LASTEST_LOCATION_NAME, Context.MODE_PRIVATE).edit()
            .putString(KEY_LATITUDE, location.latitude.toString())
            .putString(KEY_LONGITUDE, location.longitude.toString())
            .apply()
    }

    private fun getLatestLatitude() : Double {
        val latitudeStr = context.getSharedPreferences(LASTEST_LOCATION_NAME, Context.MODE_PRIVATE)
            .getString(KEY_LATITUDE, "0")
        return latitudeStr?.toDouble() ?: 0.0
    }

    private fun getLatestLongitude() : Double {
        val latitudeStr = context.getSharedPreferences(LASTEST_LOCATION_NAME, Context.MODE_PRIVATE)
            .getString(KEY_LONGITUDE, "0")
        return latitudeStr?.toDouble() ?: 0.0
    }

    interface Listener {
        fun onLocationChanged(location: Location)
    }
}