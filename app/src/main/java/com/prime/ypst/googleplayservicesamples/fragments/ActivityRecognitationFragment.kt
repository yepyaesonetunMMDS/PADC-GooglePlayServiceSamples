package com.prime.ypst.googleplayservicesamples.fragments


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.location.DetectedActivity
import com.prime.ypst.googleplayservicesamples.utils.BA_ACTIVITIES_DETECTED
import com.prime.ypst.googleplayservicesamples.utils.IE_DETECTED_ACTIVITIES
import com.prime.ypst.googleplayservicesamples.R
import com.prime.ypst.googleplayservicesamples.utils.UserActivityUtils
import kotlinx.android.synthetic.main.fragment_activity_recognitation.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ActivityRecognitationFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return ActivityRecognitationFragment()
        }
    }


    private var mActivityDetectionBR: ActivityDetectionBroadcastReceiver = ActivityDetectionBroadcastReceiver()
    private var tvActivities: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerActivityDetectionBR()
    }

    private fun registerActivityDetectionBR() {
        LocalBroadcastManager.getInstance(context!!).registerReceiver(
            mActivityDetectionBR,
            IntentFilter(BA_ACTIVITIES_DETECTED)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(mActivityDetectionBR)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_activity_recognitation, container, false)
        tvActivities = view.tvActivities

        registerActivityDetectionBR()

        return view
    }


    inner class ActivityDetectionBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val detectedActivities: ArrayList<DetectedActivity> =
                intent.getParcelableArrayListExtra(IE_DETECTED_ACTIVITIES)
            onActivitiesDetected(detectedActivities)
        }
    }

    private fun onActivitiesDetected(detectedActivities: ArrayList<DetectedActivity>) {

        val activityTextBuilder = StringBuilder()
        for (detectedActivity in detectedActivities) {
            activityTextBuilder.run {
                append(
                        UserActivityUtils.getActivityByType(
                            context!!,
                            detectedActivity.type
                        ) + " - " + detectedActivity.confidence + "% \n"
                    )
            }
        }
        tvActivities!!.text = activityTextBuilder
    }

}
