package com.permissionxl.servicetest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    private val mBinder = DownloadBinder()

    class DownloadBinder : Binder() {
        fun startDownload() {
            Log.d("MyService", "start download")
        }

        fun getProgress(): Int {
            Log.d("MyService", "getProgress")
            return 0
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }
}