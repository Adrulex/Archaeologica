package com.example.archaeologica.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import com.example.archaeologica.models.PlacemarkStore
import com.example.archaeologica.models.json.PlacemarkJSONStore
import com.example.archaeologica.models.UsersStore
import com.example.archaeologica.models.json.UsersJSONStore


class MainApp : Application(), AnkoLogger {

  lateinit var placemarks: PlacemarkStore
  lateinit var users: UsersStore

  override fun onCreate() {
    super.onCreate()
    placemarks = PlacemarkJSONStore(applicationContext)
    users = UsersJSONStore(applicationContext)
    info("Placemark started")
  }
}