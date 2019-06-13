package com.prime.ypst.googleplayservicesamples.intentservices

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.prime.ypst.googleplayservicesamples.utils.GeofenceUtils
import com.prime.ypst.googleplayservicesamples.utils.buildAndSendNotification

/**
 * Created by Ye Pyae Sone Tun
 * on 2019-06-13.
 */


class GeofenceTransitionsIntentService : IntentService(GeofenceTransitionsIntentService::class.java.simpleName) {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {

        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceUtils.getErrorString(applicationContext, geofencingEvent.errorCode)
            return
        }

        val geofenceTransitionType = geofencingEvent.geofenceTransition
        val triggeringGeofences = geofencingEvent.triggeringGeofences
        val location = geofencingEvent.triggeringLocation

        val geofenceDetails = GeofenceUtils.getGeofenceTransitionDetails(triggeringGeofences)
        val geofenceEvent =
            GeofenceUtils.getGeofenceTransition(applicationContext, geofenceTransitionType) + " - " + geofenceDetails

        // triggeringGeofence request Id
        val triggeringGeofenceId = triggeringGeofences[0].requestId


        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        when (geofenceTransitionType) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                buildAndSendNotification(applicationContext, "Geofence Enter", geofenceEvent, pendingIntent)
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                buildAndSendNotification(applicationContext, "Geofence Exit", geofenceEvent, pendingIntent)
            }
        }
    }
}