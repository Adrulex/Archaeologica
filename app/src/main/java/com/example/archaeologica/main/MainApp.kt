package com.example.archaeologica.main

import android.app.Application
import com.example.archaeologica.models.json.PlacemarkJSONStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import com.example.archaeologica.models.PlacemarkStore


class MainApp : Application(), AnkoLogger {

  lateinit var placemarks: PlacemarkStore

  override fun onCreate() {
    super.onCreate()
    placemarks = PlacemarkJSONStore(applicationContext)
    info("Placemark started")
  }
}