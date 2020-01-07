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

val JSON_FILE = "placemarks.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<PlacemarkModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class PlacemarkJSONStore(val context: Context) : PlacemarkStore, AnkoLogger {

  var placemarks = mutableListOf<PlacemarkModel>()

  init {
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<PlacemarkModel> {
    return placemarks
  }

  override fun create(placemark: PlacemarkModel) {
    placemark.id = generateRandomId()
    placemarks.add(placemark)
    serialize()
  }

  override fun update(placemark: PlacemarkModel) {
    val placemarksList = findAll() as ArrayList<PlacemarkModel>
    var foundPlacemark: PlacemarkModel? = placemarksList.find { p -> p.id == placemark.id }
    if (foundPlacemark != null) {
      foundPlacemark.title = placemark.title
      foundPlacemark.description = placemark.description
      foundPlacemark.notes = placemark.notes
      foundPlacemark.visited = placemark.visited
      foundPlacemark.datevisited = placemark.datevisited
      foundPlacemark.image1 = placemark.image1
      foundPlacemark.image2 = placemark.image2
      foundPlacemark.image3 = placemark.image3
      foundPlacemark.image4 = placemark.image4
      foundPlacemark.lat = placemark.lat
      foundPlacemark.lng = placemark.lng
      foundPlacemark.zoom = placemark.zoom
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
    val jsonString = gsonBuilder.toJson(placemarks,
      listType
    )
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    placemarks = Gson().fromJson(jsonString, listType)
  }
}
