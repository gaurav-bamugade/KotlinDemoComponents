package com.example.kotlinbase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.kotlinbase.databinding.ActivityGpsLoc2Binding
import com.example.kotlinbase.databinding.ActivityGpslocationBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_gpslocation.*
import org.w3c.dom.Text
import java.io.IOException
import java.util.*
import java.util.jar.Manifest

class GPSLocationActivity : AppCompatActivity() {
    lateinit var binding: ActivityGpslocationBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var tvLatitude:TextView
    private lateinit var tvLongitude:TextView
    lateinit var locationRequest:LocationRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_gpslocation)
        binding= ActivityGpslocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        tvLatitude=findViewById(R.id.latitude_tx)
        tvLongitude=findViewById(R.id.longitude_tx)
        //getCurrentLocation();
        gps_map_btn.setOnClickListener{
            intent= Intent(this,MapsActivity::class.java)
            intent.putExtra("longitude",tvLongitude.text)
            intent.putExtra("latitude",tvLatitude.text)
            startActivity(intent)
        }
        binding.getgps.setOnClickListener {
            checkLocationPermission()
        }
    }
    private fun checkLocationPermission(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            checkGPS()
        }

    }
    private fun checkGPS() {
        locationRequest= LocationRequest.create()
        locationRequest.priority= LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=5000
        locationRequest.fastestInterval=2000
        val builder= LocationSettingsRequest.Builder()
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
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
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
                    val geocoder= Geocoder(this, Locale.getDefault())
                    val address=geocoder.getFromLocation(location.latitude,location.longitude,1)
                    val address_line= address?.get(0)?.getAddressLine(0)
                    binding.currentAddres.setText(address_line)
                    binding.longitudeTx.setText(location.longitude.toString())
                    binding.latitudeTx.setText(location.latitude.toString())
                    val address_location= address?.get(0)?.getAddressLine(0)
                    openLocation(address_location.toString())
                }
                catch (e: IOException)
                {

                }
            }
        }
    }
    private fun openLocation(location: String) {
        binding.longitudeTx.setOnClickListener {
            if(!binding.longitudeTx.text.isEmpty()){
                val uri= Uri.parse("geo:0, 0??q=$location")
                val  intent= Intent(Intent.ACTION_VIEW,uri)
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
        }
    }









/*  private  fun  getCurrentLocation()
    {
        if(checkPermissions())
        {
            if(isLocationEnabled())
            {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task->
                    val location:Location?=task.result
                    if(location==null)
                    {
                        Toast.makeText(this,"Null received",Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        Toast.makeText(this,"get success",Toast.LENGTH_SHORT).show()
                        tvLatitude.text=""+location.latitude
                        tvLongitude.text=""+location.longitude
                    }

                }
            }
            else
            {
                //setting manuyally here
                Toast.makeText(this,"TURN ON LOCATION",Toast.LENGTH_SHORT).show()
                val intent =Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else
        {
            //request permission here
            requestPermission()
        }
    }
    private fun isLocationEnabled():Boolean{
        val locationManager:LocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100

    }
    private fun checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION)
        {
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"PERMISSION GRANTED",Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }
            else
            {
                Toast.makeText(this,"PERMISSION DENIED",Toast.LENGTH_SHORT).show()

            }
        }
    }*/

}