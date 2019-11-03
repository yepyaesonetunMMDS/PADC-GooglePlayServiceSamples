package com.prime.ypst.googleplayservicesamples.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.EditText
import com.prime.ypst.googleplayservicesamples.*
import com.prime.ypst.googleplayservicesamples.fragments.*
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
            R.id.nav_barcode_scan -> {
                fragment = MLKitBarCodeFragment.newInstance()
                toolbar.title = "MLKit BarCode Detect"
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
