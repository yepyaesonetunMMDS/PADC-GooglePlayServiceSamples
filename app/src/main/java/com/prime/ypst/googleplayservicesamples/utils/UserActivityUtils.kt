package com.prime.ypst.googleplayservicesamples.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.DetectedActivity
import com.prime.ypst.googleplayservicesamples.R
import com.prime.ypst.googleplayservicesamples.intentservices.DetectedActivitiesIntentService

class UserActivityUtils {

    companion object {
        fun getActivityByType(context: Context, activityType: Int): String {
            val resources = context.resources
            when (activityType) {
                DetectedActivity.IN_VEHICLE -> return resources.getString(R.string.in_vehicle)
                DetectedActivity.ON_BICYCLE -> return resources.getString(R.string.on_bicycle)
                DetectedActivity.ON_FOOT -> return resources.getString(R.string.on_foot)
                DetectedActivity.RUNNING -> return resources.getString(R.string.running)
                DetectedActivity.STILL -> return resources.getString(R.string.still)
                DetectedActivity.TILTING -> return resources.getString(R.string.tilting)
                DetectedActivity.UNKNOWN -> return resources.getString(R.string.unknown)
                DetectedActivity.WALKING -> return resources.getString(R.string.walking)
                else -> return resources.getString(R.string.unidentifiable_activity)
            }
        }

        fun getActivityDetectionPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, DetectedActivitiesIntentService::class.java)
            return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
}