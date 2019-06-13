package com.prime.ypst.googleplayservicesamples.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.prime.ypst.googleplayservicesamples.*
import com.prime.ypst.googleplayservicesamples.fragments.ActivityRecognitationFragment
import com.prime.ypst.googleplayservicesamples.fragments.CurrentLocationFragment
import com.prime.ypst.googleplayservicesamples.fragments.GeofencesFragment
import com.prime.ypst.googleplayservicesamples.fragments.MapFragment
import com.prime.ypst.googleplayservicesamples.utils.locationPermissionsAreGranted
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val MY_LOCATION_REQUEST_CODE = 329
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        if (locationPermissionsAreGranted(this)) {
            // do nothing
        } else {
            showLocationPermissionWarningDialog()
        }

        navView.setNavigationItemSelectedListener(this)

        // initial fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fl_container,
                    CurrentLocationFragment.newInstance(),
                    CurrentLocationFragment.TAG
                )
                .commit()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_LOCATION_REQUEST_CODE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
        } else {
        }
    }

    private fun checkForLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissions()
        } else {
        }
    }


    private fun requestLocationPermissions() = showLocationPermissionRequestDialog()

    private fun showLocationPermissionWarningDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.location_permission_dialog_title))
            .setMessage(getString(R.string.location_permission_dialog_message))
            .setPositiveButton(getString(R.string.location_permission_dialog_ok_button)) { _, _ ->
                showLocationPermissionRequestDialog()
            }.create().show()
    }

    private fun showLocationPermissionRequestDialog() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_LOCATION_REQUEST_CODE
        )
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_home -> {
                fragment = CurrentLocationFragment.newInstance()
                toolbar.title = "Location"
            }
            R.id.nav_activituy_recognition -> {
                fragment = ActivityRecognitationFragment.newInstance()
                toolbar.title = "Activity Recognition"
            }
            R.id.nav_google_map -> {
                fragment = MapFragment.newInstance()
                toolbar.title = "Google Map"
            }
            R.id.nav_geofences -> {
                fragment = GeofencesFragment.newInstance()
                toolbar.title = "Geofences"
            }
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragment?.let { fragmentTransaction.replace(R.id.fl_container, it) }
        fragmentTransaction.commit()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
