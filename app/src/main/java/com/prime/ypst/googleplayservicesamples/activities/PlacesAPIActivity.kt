package com.prime.ypst.googleplayservicesamples.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.prime.ypst.googleplayservicesamples.R
import kotlinx.android.synthetic.main.activity_places_api.*

class PlacesAPIActivity : AppCompatActivity() {

    private lateinit var placeClient: PlacesClient
    private lateinit var mMap: GoogleMap
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_api)

        val apiKey = "AIzaSyBmQQeq_R-ASzVYvbsMtOKfUrGf-ok4Y4I"

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        placeClient = Places.createClient(this)


//        this.let {
//            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(it)
//        }



        val autoCompleteSupportFragment: AutocompleteSupportFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autoCompleteSupportFragment.setPlaceFields(
           listOf(
                Place.Field.ID,
                Place.Field.LAT_LNG,
                Place.Field.NAME
            )
        )

        autoCompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            @SuppressLint("SetTextI18n")
            override fun onPlaceSelected(place: Place) {
                var latLng = place.latLng

                tvName.text = place.name
                tvLatLng.text = "Lat: ${latLng?.latitude}, Lng: ${latLng?.longitude}"

                setUpMap(latLng = latLng!!)

                Toast.makeText(
                    applicationContext,
                    "Selected Lat,Lng: ${latLng!!.latitude},${latLng.longitude}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onError(status: Status) {
                Toast.makeText(applicationContext,"${status.statusMessage}",Toast.LENGTH_LONG).show()
            }

        })
    }


    @SuppressLint("MissingPermission")
    private fun setUpMap(latLng: LatLng) {
        val mapFragment = fragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            mMap = it
            mMap.uiSettings.isZoomControlsEnabled = true

            val location = latLng

            putMarkerOnLocation(location.latitude, location.longitude, "Here I'm")
            focusTotLocation(location.latitude, location.longitude)
        }

    }

    private fun focusTotLocation(latitude: Double, longitude: Double) {
        val cameraPosition = CameraPosition.Builder()
            .target(
                LatLng(
                    latitude, longitude
                )
            )      // Sets the center of the map to location user
            .zoom(20f)                   // Sets the zoom
            .bearing(90f)                // Sets the orientation of the camera to east
            .tilt(40f)                   // Sets the tilt of the camera to 30 degrees
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

//        val latLng = LatLng(latitude, longitude)
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f))
    }

    private fun putMarkerOnLocation(latitude: Double, longitude: Double, name: String) {
        val location = LatLng(latitude, longitude)
        if (::mMap.isInitialized) mMap.addMarker(MarkerOptions().position(location).title(name))
    }
}
