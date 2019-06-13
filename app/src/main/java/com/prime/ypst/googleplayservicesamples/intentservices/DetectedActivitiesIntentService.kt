package com.prime.ypst.googleplayservicesamples.intentservices

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity
import com.prime.ypst.googleplayservicesamples.utils.BA_ACTIVITIES_DETECTED
import com.prime.ypst.googleplayservicesamples.utils.IE_DETECTED_ACTIVITIES
import java.util.ArrayList

class DetectedActivitiesIntentService : IntentService("DetectedActivitiesIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val result = ActivityRecognitionResult.extractResult(intent)
        val detectedActivities = result.probableActivities as ArrayList<DetectedActivity>

        val localIntent = Intent(BA_ACTIVITIES_DETECTED)
        localIntent.putExtra(IE_DETECTED_ACTIVITIES, detectedActivities)
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent)
    }

}