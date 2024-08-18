package com.example.bluetoothtest

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.companion.AssociationInfo
import android.companion.AssociationRequest
import android.companion.BluetoothDeviceFilter
import android.companion.CompanionDeviceManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.ParcelUuid
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.permissionxl.lihuajundev.PermissionXL
import java.util.UUID
import java.util.concurrent.Executor
import java.util.regex.Pattern

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private val  TAG = "MainActivity"
    lateinit var deviceListAdapter: DeviceListAdapter
    val deviceList: ArrayList<BluetoothDevice> = ArrayList()

    private val deviceManager: CompanionDeviceManager by lazy {
        getSystemService(Context.COMPANION_DEVICE_SERVICE) as CompanionDeviceManager
    }
    val bluetoothAdapter: BluetoothAdapter by lazy {
        val java = BluetoothManager::class.java
        getSystemService(java)!!.adapter }
    val executor: Executor =  Executor { it.run() }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val onBtn: Button = findViewById(R.id.onBtn)
        val discoverableBtn: Button = findViewById(R.id.discoverableBtn)
        val findUnPairedDevice: Button = findViewById(R.id.findUnPairedDevice)
        val lsDevice: ListView = findViewById(R.id.lsDevice)


        PermissionXL.request(this,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT) { allGranted, deniedList ->
                    if (allGranted) {
                        Toast.makeText(this, "All permission are granted.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "You denied $deniedList", Toast.LENGTH_SHORT)
                            .show()
                    }
            }

        onBtn.setOnClickListener {
            Log.d(TAG, "onclick enable button")
            enableDisableBT()
        }

        discoverableBtn.setOnClickListener {
            Log.d(TAG, "onclick discover button")
            discoverable()
        }

        findUnPairedDevice.setOnClickListener {
            Log.d(TAG, "onclick btnDiscover button")
            btnDiscover()
        }

        val intentFilter = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        registerReceiver(receiver4, intentFilter)

        lsDevice.onItemClickListener = this


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun enableDisableBT() {
        if (bluetoothAdapter == null) {
            Log.d(TAG, "Not BT capabilities")
        }
        if (!bluetoothAdapter.isEnabled) {
            Log.d(TAG, "enable bluetooth")
            val enableBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            startActivity(enableBTIntent)

            val BTintent = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            registerReceiver(receiver, BTintent)
        }

    }

    private fun discoverable() {
        Log.d(TAG, "discover 300 seconds")
        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_ADVERTISE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        startActivity(discoverableIntent)

        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
        registerReceiver(receiver2, intentFilter)
    }

    private fun btnDiscover() {
        Log.d(TAG, "looking for unpair device")
        if (bluetoothAdapter.isDiscovering) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            bluetoothAdapter.cancelDiscovery()
            Log.d(TAG, "cancel discover")

            bluetoothAdapter.startDiscovery()
            val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(receiver3, intentFilter)
        }

        if (!bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.startDiscovery()
            val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(receiver3, intentFilter)
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.action
            when (action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val stata = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                    when (stata) {
                        BluetoothAdapter.STATE_OFF -> {Log.d(TAG, "SATE OFF")}
                        BluetoothAdapter.STATE_TURNING_OFF -> {Log.d(TAG, "SATE TURNING OFF")}
                        BluetoothAdapter.STATE_ON -> {Log.d(TAG, "SATE ON")}
                        BluetoothAdapter.STATE_TURNING_ON -> {Log.d(TAG, "SATE TURNING ON")}
                    }
                }
            }
        }

    }

    private val receiver2 = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.action
            when (action) {
                BluetoothAdapter.ACTION_SCAN_MODE_CHANGED -> {
                    val mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR)
                    when (mode) {
                        BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> {Log.d(TAG, "SCAN_MODE_CONNECTABLE_DISCOVERABLE")}
                        BluetoothAdapter.SCAN_MODE_CONNECTABLE -> {Log.d(TAG, "SCAN_MODE_CONNECTABLE")}
                        BluetoothAdapter.SCAN_MODE_NONE -> {Log.d(TAG, "SCAN_MODE_NONE")}
                        BluetoothAdapter.STATE_CONNECTING -> {Log.d(TAG, "STATE_CONNECTING ")}
                        BluetoothAdapter.STATE_CONNECTED -> {Log.d(TAG, "STATE_CONNECTED")}
                    }
                }
            }
        }

    }

    private val receiver3 = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        if (device != null) {
                            deviceList.add(device)
                            if (ActivityCompat.checkSelfPermission(
                                    context!!,
                                    Manifest.permission.BLUETOOTH_CONNECT
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                return
                            }
                            Log.d(TAG, "received:  ${device.name}    ${device.address}")
                            deviceListAdapter = DeviceListAdapter(context, R.layout.device_adapter_view, deviceList)
                            val lsDevice : ListView = findViewById(R.id.lsDevice)
                            lsDevice.adapter = deviceListAdapter
                    }
                }
            }
        }
    }

    private val receiver4 = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.action
            when (action) {
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    val mode: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (ActivityCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    when (mode?.bondState) {
                        BluetoothDevice.BOND_NONE -> {Log.d(TAG, "BOND_NONE")}
                        BluetoothDevice.BOND_BONDED -> {Log.d(TAG, "BOND_BONDED")}
                        BluetoothDevice.BOND_BONDING -> {Log.d(TAG, "BOND_BONDING")}
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "destroy call")
        unregisterReceiver(receiver)
        unregisterReceiver(receiver2)
        unregisterReceiver(receiver3)
        unregisterReceiver(receiver4)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        bluetoothAdapter.cancelDiscovery()

        Log.d(TAG, "onItemClick:  you click a device")
        val name = deviceList[position].name
        val address = deviceList[position].address
        Log.d(TAG, "onItemClick: name is $name")
        Log.d(TAG, "onItemClick: address is $address")

        val deviceFilter: BluetoothDeviceFilter = BluetoothDeviceFilter.Builder()
            // Match only Bluetooth devices whose name matches the pattern.
            .setNamePattern(Pattern.compile(name))
            // Match only Bluetooth devices whose service UUID matches this pattern.
            .addServiceUuid(ParcelUuid(UUID(0x123abcL, -1L)), null)
            .build()

        val pairingRequest: AssociationRequest = AssociationRequest.Builder()
            // Find only devices that match this request filter.
            .addDeviceFilter(deviceFilter)
            // Stop scanning as soon as one device matching the filter is found.
            .setSingleDevice(true)
            .build()

        deviceManager.associate(pairingRequest,
            executor,
            object : CompanionDeviceManager.Callback() {
                // Called when a device is found. Launch the IntentSender so the user
                // can select the device they want to pair with.
                override fun onAssociationPending(intentSender: IntentSender) {
                    intentSender?.let {
                        registerForActivityResult(it,)
                    }
                }

                override fun onAssociationCreated(associationInfo: AssociationInfo) {
                    // The association is created.
                }

                override fun onFailure(errorMessage: CharSequence?) {
                    // Handle the failure.
                }
            })

        Log.d(TAG, "try pair with $name")
        deviceList[position].createBond()
    }
}