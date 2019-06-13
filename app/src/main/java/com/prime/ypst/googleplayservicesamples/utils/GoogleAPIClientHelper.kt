package com.prime.ypst.googleplayservicesamples.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.ActivityRecognition
import com.prime.ypst.googleplayservicesamples.ControllerActivityRecoginition

class GoogleAPIClientHelper(private var context: Context) :
    ControllerActivityRecoginition,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    private var mActivityRecognitionResultCallback: ActivityRecognitionResultCallback =
        ActivityRecognitionResultCallback()
    private lateinit var mGoogleApiClient: GoogleApiClient


    override fun requestActivityUpdate() {

        if (!mGoogleApiClient.isConnected) {
            Toast.makeText(context, "Google Api Client is not connected", Toast.LENGTH_LONG).show()
        } else {

            val result = ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                mGoogleApiClient,
                ACTIVITY_DETECTION_INTERVAL.toLong(),
                UserActivityUtils.getActivityDetectionPendingIntent(
                    context
                )
            )

            result.setResultCallback(this.mActivityRecognitionResultCallback)
        }

    }

    override fun removeActivityUpdate() {

        if (!mGoogleApiClient.isConnected) {
            Toast.makeText(context, "Google Api Client is not connected", Toast.LENGTH_SHORT).show()
        } else {
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(
                mGoogleApiClient,
                UserActivityUtils.getActivityDetectionPendingIntent(
                    context
                )
            ).setResultCallback(this.mActivityRecognitionResultCallback)

        }
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: GoogleAPIClientHelper? = null

        fun initGoogleApiClientHelper(context: Context) {
            INSTANCE =
                GoogleAPIClientHelper(context)
        }
    }

    init {
        buildGoogleApiClient(context)
        connnect()
    }

    private fun buildGoogleApiClient(context: Context) {
        mGoogleApiClient = GoogleApiClient.Builder(context)
            .addApi(ActivityRecognition.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
    }


    fun connnect() {
        mGoogleApiClient.connect()
        Log.d("GC", "Connected")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("GAPIC", "onConnectionFailed: connectionResult = $p0")
        removeActivityUpdate()
    }

    override fun onConnected(bundle: Bundle?) {
        Log.d("GAPIC", "OnConnected")
        requestActivityUpdate()
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d("GAPIC", "onConnectionSuspended: googleApiClient.connect()")
        mGoogleApiClient.connect()
    }

    inner class ActivityRecognitionResultCallback : ResultCallback<Status> {

        override fun onResult(status: Status) {
            if (status.isSuccess) {
                Log.e("AR", "Success")
            } else {
                Log.e("AR", "Fail")
            }
        }
    }
}


