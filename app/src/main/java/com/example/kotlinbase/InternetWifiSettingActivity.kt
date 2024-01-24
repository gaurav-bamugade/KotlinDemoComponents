package com.example.kotlinbase

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_internet_wifi_setting.*
import java.util.Observer

class InternetWifiSettingActivity : AppCompatActivity() {
    lateinit var wifiManager: WifiManager
    private lateinit var network:NetworkConnection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet_wifi_setting)
        //val networkConnection=NetworkConnection(applicationContext)
        checkNetworkConnection()
      /*  networkConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
            if(isConnected)
            {
                disconnected_wifi_ln.visibility= View.GONE
                conected_wifi_ln.visibility=View.VISIBLE
            }
            else
            {
                disconnected_wifi_ln.visibility= View.VISIBLE
                conected_wifi_ln.visibility=View.GONE
            }
        })*/
        wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        turn_wifi_on_btn.setOnClickListener {
            wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifiManager.isWifiEnabled = true
            Toast.makeText(this, "Wifi enabled", Toast.LENGTH_SHORT).show()
        }
        turn_wifi_off_btn.setOnClickListener {
            wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifiManager.isWifiEnabled = false
            Toast.makeText(this, "Wifi disabled", Toast.LENGTH_SHORT).show()

        }
    }
    private fun checkNetworkConnection(){
        network= NetworkConnection(application)
        network.observe(this) { isConnected ->
            if (isConnected) {
                disconnected_wifi_ln.visibility = View.GONE
                conected_wifi_ln.visibility = View.VISIBLE
            } else {
                disconnected_wifi_ln.visibility = View.VISIBLE
                conected_wifi_ln.visibility = View.GONE
            }
        }
    }

}