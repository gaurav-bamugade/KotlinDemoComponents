package com.example.kotlinbase

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationManager : LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  getgeoloca()
        val pairedDevicesArrayAdapter = ArrayAdapter<String>(this, R.layout.device_nm)
        internet_wifi_setting_btn.setOnClickListener {
            intent = Intent(applicationContext, InternetWifiSettingActivity::class.java)
            startActivity(intent)
        }
       // locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        gps_location_btn.setOnClickListener {
           intent = Intent(applicationContext, GPSLocationActivity::class.java)
            startActivity(intent)
            //getgeoloca()
            //locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        }

        camera_x.setOnClickListener {
            intent = Intent(applicationContext, CameraXActivity::class.java)
            startActivity(intent)
        }

        bluetooth_1.setOnClickListener {
            intent = Intent(applicationContext, Bluetooth1::class.java)
            startActivity(intent)
        }

    }
/*    private fun getgeoloca(){
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this,"Null received", Toast.LENGTH_SHORT).show()

            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task->
            val location: Location?=task.result
            if(location==null)
            {
                Toast.makeText(this,"Null received", Toast.LENGTH_SHORT).show()

            }
            else
            {
                Toast.makeText(this,"get success", Toast.LENGTH_SHORT).show()

            }

        }
    }*/
/*    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            //thetext.text = ("" + location.longitude + ":" + location.latitude)

        }
        fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        fun onProviderEnabled(provider: String) {}
        fun onProviderDisabled(provider: String) {}
    }*/
}


