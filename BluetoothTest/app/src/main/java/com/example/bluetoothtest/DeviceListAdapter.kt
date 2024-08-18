package com.example.bluetoothtest

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.app.ActivityCompat


class DeviceListAdapter(
    context: Context,
    private val resourceId: Int,
    data: ArrayList<BluetoothDevice>
) : ArrayAdapter<BluetoothDevice>(context, resourceId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId, parent, false)
        val  tvDeviceName: TextView = view.findViewById(R.id.tvDeviceName)
        val  tvDeviceAddress: TextView = view.findViewById(R.id.tvDeviceAddress)
        val device = getItem(position)

        if (device != null) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return view
            }
            tvDeviceName.text = device.name
            tvDeviceAddress.text = device.address
        }

        return view
    }
}