package com.example.archaeologica.main

import android.app.Application
import com.example.archaeologica.models.PlacemarkStore
import com.example.archaeologica.models.firebase.PlacemarkFireStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

  lateinit var placemarks: PlacemarkStore

  override fun onCreate() {
    super.onCreate()
    placemarks = PlacemarkFireStore(applicationContext)
    info("Placemark started")
  }
}