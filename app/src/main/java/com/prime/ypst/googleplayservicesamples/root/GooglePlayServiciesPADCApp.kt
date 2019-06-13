package com.prime.ypst.googleplayservicesamples.root

import android.app.Application
import com.prime.ypst.googleplayservicesamples.utils.GoogleAPIClientHelper

/**
 * Created by Ye Pyae Sone Tun
 * on 2019-06-13.
 */
class GooglePlayServiciesPADCApp: Application() {
    override fun onCreate() {
        super.onCreate()

        GoogleAPIClientHelper.initGoogleApiClientHelper(
            applicationContext
        )
    }
}