package com.example.viewmodeltest

import androidx.lifecycle.ViewModel

class MainViewModel(countReserved: Int) : ViewModel() {
    var counter = countReserved
}