package com.example.viewmodeltest

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

class MyObserver : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        Log.d("MyObserver", "ActivityStart")
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.d("MyObserver", "ActivityStop")
    }
}