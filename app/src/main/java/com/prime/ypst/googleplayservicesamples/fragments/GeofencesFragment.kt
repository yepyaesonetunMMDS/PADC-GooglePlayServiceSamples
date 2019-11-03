package com.prime.ypst.googleplayservicesamples.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
import com.prime.ypst.googleplayservicesamples.R
import com.prime.ypst.googleplayservicesamples.utils.GeofenceUtils
import kotlinx.android.synthetic.main.fragment_geofence.view.*

/**
 * A simple [Fragment] subclass.
 *
 */

class GeofencesFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return GeofencesFragment()
        }
    }

    private lateinit var tvLocations: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_geofence, container, false)
        tvLocations = view.textViewLocation

        prepareGeoPoints()

        return view
    }

    private fun prepareGeoPoints() {
        val locationList = arrayListOf<LatLng>()
        val location1 = LatLng(16.844606, 96.205229)
        val location2 = LatLng(16.845078, 96.205702)
        val location3 = LatLng(16.844031, 96.204588)
        val location4 = LatLng(16.845217, 96.205830)
        val location5 = LatLng(16.843811, 96.204959)
        val location6 = LatLng(16.843946, 96.205105)
        val location7 = LatLng(16.8413919, 96.2038901)
        val location8 = LatLng(16.8406311, 96.2037158)
        val location9 = LatLng(16.840623, 96.202959)
        val location10 = LatLng(16.839805, 96.202145)
        locationList.add(location1)
        locationList.add(location2)
        locationList.add(location3)
        locationList.add(location4)
        locationList.add(location5)
        locationList.add(location6)
        locationList.add(location7)
        locationList.add(location8)
        locationList.add(location9)
        locationList.add(location10)

        GeofenceUtils(context!!).addTestGeofence(locationList)

        val locationListTextBuilder = StringBuilder()

        for (location in locationList) {
            locationListTextBuilder.run {
                append("Lat: ${location.latitude} and Lng: ${location.longitude} \n")
            }
        }
        tvLocations.text = locationListTextBuilder

    }


}
