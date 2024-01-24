package com.example.kotlinbase

import android.Manifest

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_device_list.*


class DeviceListActivity : AppCompatActivity() {
    private val TAG = "DeviceListActivity"
    var EXTRA_DEVICE_ADDRESS = "device_address"
    private var mBtAdapter: BluetoothAdapter? = null
    private var mNewDevicesArrayAdapter: ArrayAdapter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)

       /* button_scan.setOnClickListener{
            doDiscovery();
            it.setVisibility(View.GONE);
        }
        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        val pairedDevicesArrayAdapter = ArrayAdapter<String>(this,  R.layout.device_nm)
        mNewDevicesArrayAdapter = ArrayAdapter(this,  R.layout.device_nm)
        // Find and set up the ListView for paired devices
        // Find and set up the ListView for paired devices
        val pairedListView: ListView = findViewById(R.id.paired_devices)
        pairedListView.setAdapter(pairedDevicesArrayAdapter)
        pairedListView.setOnItemClickListener(mDeviceClickListener)

        // Find and set up the ListView for newly discovered devices

        // Find and set up the ListView for newly discovered devices
        val newDevicesListView: ListView = findViewById(R.id.new_devices)
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter)
        newDevicesListView.setOnItemClickListener(mDeviceClickListener)

        // Register for broadcasts when a device is discovered

        // Register for broadcasts when a device is discovered
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        this.registerReceiver(mReceiver, filter)

        // Register for broadcasts when discovery has finished

        // Register for broadcasts when discovery has finished
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        this.registerReceiver(mReceiver, filter)

        // Get the local Bluetooth adapter

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()

        // Get a set of currently paired devices

        // Get a set of currently paired devices
        val pairedDevices = if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
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
        } else {

        }
        mBtAdapter!!.getBondedDevices()

        // If there are paired devices, add each one to the ArrayAdapter

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size > 0) {
            findViewById<View>(R.id.title_paired_devices).visibility = View.VISIBLE
            for (device in pairedDevices) {
                pairedDevicesArrayAdapter.add(
                    """
                ${device.name}
                ${device.address}
                """.trimIndent()
                )
            }
        } else {
            val noDevices = resources.getText(R.string.none_paired).toString()
            pairedDevicesArrayAdapter.add(noDevices)
        }*/
    }
   /* private fun doDiscovery() {
        Log.d(TAG, "doDiscovery()")
        setProgressBarIndeterminateVisibility(true)
        setTitle("Scanning")
        // Turn on sub-title for new devices
         title_new_devices.visibility = View.VISIBLE

        // If we're already discovering, stop it
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
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
        if (mBtAdapter!!.isDiscovering) {
            mBtAdapter!!.cancelDiscovery()
        }
        // Request discover from BluetoothAdapter
        mBtAdapter!!.startDiscovery()
    }
    private val mDeviceClickListener =
        OnItemClickListener { av, v, arg2, arg3 -> // Cancel discovery because it's costly and we're about to connect
            mBtAdapter?.cancelDiscovery()

            // Get the device MAC address, which is the last 17 chars in the View
            val info = (v as TextView).text.toString()
            val address = info.substring(info.length - 17)

            // Create the result Intent and include the MAC address
            val intent = Intent()
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address)

            // Set result and finish this Activity
            setResult(RESULT_OK, intent)
            finish()
        }
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from the Intent
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                // If it's already paired, skip it, because it's been listed already
                if (ActivityCompat.checkSelfPermission(
                        this@DeviceListActivity,
                        Manifest.permission.BLUETOOTH_CONNECT
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
                if (device != null && device.bondState != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter!!.add(
                        """
                        ${device.name}
                        ${device.address}
                        """.trimIndent()
                    )
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                setProgressBarIndeterminateVisibility(false)
                setTitle(R.string.select_device)
                if (mNewDevicesArrayAdapter!!.count == 0) {
                    val noDevices = resources.getText(R.string.none_found).toString()
                    mNewDevicesArrayAdapter!!.add(noDevices)
                }
            }
        }
    }*/

}