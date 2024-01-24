package com.example.kotlinbase

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_bluetooth1.*

class Bluetooth1 : AppCompatActivity() {
//lateinit var badapter:BluetoothAdapter
private val REQUEST_CODE_ENABLE_BT:Int=1
private val  REQUEST_CODE_DISCOVERABLE_BT:Int=2


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth1)
        checkpermission()
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)

        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.getAdapter()

        if(bluetoothAdapter==null)
        {
            bluetooth_status.text="bluetooth is not available"
        }
        else
        {
            bluetooth_status.text="bluetooth is available"
        }

        turn_on_bl.setOnClickListener {
            if(bluetoothAdapter!!.isEnabled)
            {
                Toast.makeText(this,"Already on",Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent= Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(intent,REQUEST_CODE_ENABLE_BT)
            }
        }

        turn_of_bl.setOnClickListener {
            if(!bluetoothAdapter!!.isEnabled)
            {
                Toast.makeText(this,"Already off",Toast.LENGTH_SHORT).show()
            }
            else
            {
                bluetoothAdapter.disable()
                Toast.makeText(this,"bluetoot turned off",Toast.LENGTH_SHORT).show()

            }
        }
        discoverable.setOnClickListener {
            if(!bluetoothAdapter!!.isDiscovering)
            {
                Toast.makeText(this,"making your device discoverable",Toast.LENGTH_SHORT).show()
                val intent=Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent,REQUEST_CODE_DISCOVERABLE_BT)
            }
        }
        get_paird_devices.setOnClickListener {
            if(bluetoothAdapter!!.isEnabled)
            {
                paird_tv.text="paird devices"
                val devices= bluetoothAdapter.bondedDevices
                for(device in devices)
                {
                    val deviceName=device.name
                    val deviceAddress=device
                    paird_tv.append("\nDevice: $deviceName, $device")
                }
            }
            else
            {
                Toast.makeText(this,"turn on bluiettothj",Toast.LENGTH_SHORT).show()

            }
        }
            // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)



    }
    private val receiver = object : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action: String = intent.action!!
        when(action) {
            BluetoothDevice.ACTION_FOUND -> {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                val deviceName = if (ActivityCompat.checkSelfPermission(
                        this@Bluetooth1,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                } else {

                }
                device.name
                val deviceHardwareAddress = device.address // MAC address
            }
        }
    } }
    // Create a BroadcastReceiver for ACTION_FOUND.

    private fun checkpermission()
    {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(this@Bluetooth1,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT,Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_SCAN,Manifest.permission.BLUETOOTH_ADMIN), REQUEST_CODE_ENABLE_BT);
                return;
            }
        }
       /* if (ContextCompat.checkSelfPermission(this@Bluetooth1, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(this@Bluetooth1,  String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }*/

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                             permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_ENABLE_BT -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {


                } else {

                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_CODE_ENABLE_BT->
                if(requestCode== Activity.RESULT_OK){
                    Toast.makeText(this,"bluetoot is on",Toast.LENGTH_SHORT).show()
                }
            else
                {
                    Toast.makeText(this,"could not on bluetooth",Toast.LENGTH_SHORT).show()

                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}