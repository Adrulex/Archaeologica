package com.example.archaeologica.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import com.example.archaeologica.helpers.*
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.models.PlacemarkStore
import java.util.*

val PLACEMARK_JSON_FILE = "placemarks.json"
val placemarkgsonBuilder = GsonBuilder().setPrettyPrinting().create()
val placemarklistType = object : TypeToken<ArrayList<PlacemarkModel>>() {}.type

fun placemarkgenerateRandomId(): Long {
  return Random().nextLong()
}

class PlacemarkJSONStore(val context: Context) : PlacemarkStore, AnkoLogger {

  var placemarks = mutableListOf<PlacemarkModel>()

  init {
    if (exists(context, PLACEMARK_JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<PlacemarkModel> {
    return placemarks
  }

  override fun create(placemark: PlacemarkModel) {
    placemark.id = placemarkgenerateRandomId()
    placemarks.add(placemark)
    serialize()
  }

  override fun update(placemark: PlacemarkModel) {
    val placemarksList = findAll() as ArrayList<PlacemarkModel>
    val foundPlacemark: PlacemarkModel? = placemarksList.find { p -> p.id == placemark.id }
    if (foundPlacemark != null) {
      foundPlacemark.title = placemark.title
      foundPlacemark.description = placemark.description
      foundPlacemark.notes = placemark.notes
      foundPlacemark.visited = placemark.visited
      foundPlacemark.datevisited = placemark.datevisited
      foundPlacemark.lat = placemark.lat
      foundPlacemark.lng = placemark.lng
      foundPlacemark.zoom = placemark.zoom
      if(placemark.image1 != "") foundPlacemark.image1 = placemark.image1
      if(placemark.image2 != "") foundPlacemark.image2 = placemark.image2
      if(placemark.image3 != "") foundPlacemark.image3 = placemark.image3
      if(placemark.image4 != "") foundPlacemark.image4 = placemark.image4
    }
    serialize()
  }

  override fun delete(placemark: PlacemarkModel) {
    placemarks.remove(placemark)
    serialize()
  }

  override fun findById(id:Long) : PlacemarkModel? {
    val foundPlacemark: PlacemarkModel? = placemarks.find { it.id == id }
    return foundPlacemark
  }

  private fun serialize() {
    val jsonString = placemarkgsonBuilder.toJson(placemarks,
      placemarklistType
    )
    write(context, PLACEMARK_JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, PLACEMARK_JSON_FILE)
    placemarks = Gson().fromJson(jsonString, placemarklistType)
  }
}
