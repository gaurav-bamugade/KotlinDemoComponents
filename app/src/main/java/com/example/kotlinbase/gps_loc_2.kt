package com.example.kotlinbase

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.kotlinbase.databinding.ActivityGpsLoc2Binding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_gps_loc2.*
import java.io.IOError
import java.io.IOException
import java.util.*

class gps_loc_2 : AppCompatActivity() {
    lateinit var binding: ActivityGpsLoc2Binding
    lateinit var locationRequest:LocationRequest
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_gps_loc2)
        binding=ActivityGpsLoc2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

        binding.gpsLocBtn.setOnClickListener {
            checkLocationPermission()
        }
        //main_latitude_tx.text=""+location.latitude
       // main_long_tx.text=""+location.longitude

    }
    private fun checkLocationPermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            checkGPS()
        }

    }

    private fun checkGPS() {
      locationRequest=LocationRequest.create()
        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=5000
        locationRequest.fastestInterval=2000
        val builder=LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val result=LocationServices.getSettingsClient(this.applicationContext)
            .checkLocationSettings(builder.build())

        result.addOnCompleteListener { task ->
            try {
                val response=task.getResult(ApiException::class.java)
                getCurentUSerLocation()
            }
            catch (e : ApiException){
                when(e.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED->try {

                    }catch (sendIntentException : IntentSender.SendIntentException)
                    {

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE->{

                       }
                }
            }
        }

    }

    private fun getCurentUSerLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener{ task->
            val location= task.getResult()
            if(location!=null)

            {
                try {
                    val geocoder=Geocoder(this, Locale.getDefault())
                    val address=geocoder.getFromLocation(location.latitude,location.longitude,1)
                    val address_line= address!![0].getAddressLine(0)
                    binding.mainLatitudeTx.setText(address_line)
                    binding.mainLongTx.setText(location.latitude.toString()+" "+location.longitude.toString())
                    val address_location= address?.get(0)?.getAddressLine(0)
                    openLocation(address_location.toString())
                }
                catch (e:IOException)
                {

                }
            }
       }
    }
    private fun openLocation(location: String) {
        binding.mainLongTx.setOnClickListener {
            if(!binding.mainLongTx.text.isEmpty()){
                val uri=Uri.parse("geo:0, 0??q=$location")
                val  intent= Intent(Intent.ACTION_VIEW,uri)
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
        }
    }

}