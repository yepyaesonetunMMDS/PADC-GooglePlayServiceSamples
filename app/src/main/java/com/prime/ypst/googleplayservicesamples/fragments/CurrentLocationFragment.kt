package com.prime.ypst.googleplayservicesamples.fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.prime.ypst.googleplayservicesamples.R
import com.prime.ypst.googleplayservicesamples.activities.PlacesAPIActivity
import com.prime.ypst.googleplayservicesamples.utils.locationPermissionsAreGranted

/**
 * A simple [Fragment] subclass.
 *
 */
class CurrentLocationFragment : Fragment() {

    companion object {
        const val TAG = "CurrentLocationFragment"
        fun newInstance(): Fragment {
            return CurrentLocationFragment()
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_current_location, container, false)
        val tvLocation = view.findViewById<TextView>(R.id.tvMyLocation)
        val btn = view.findViewById<Button>(R.id.button)
        val btnPlaceAPI = view.findViewById<Button>(R.id.btnPlacesAutoCompleteAPI)

        if (locationPermissionsAreGranted(context!!)) {
            setCurrentLocation(tvLocation)
        }

        btn.setOnClickListener { setCurrentLocation(tvLocation) }

        btnPlaceAPI.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    PlacesAPIActivity::class.java
                )
            )
        }

        return view
    }


    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun setCurrentLocation(tvLocation: TextView) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->

                Log.e("location", location.toString())
                tvLocation.text =
                    "MyLocationIs: Lat=> ${location!!.latitude} & Lng=> ${location!!.longitude}"
            }
            .addOnFailureListener {
                Log.e("location", it.localizedMessage)
                tvLocation.text = it.localizedMessage
            }
    }


}
