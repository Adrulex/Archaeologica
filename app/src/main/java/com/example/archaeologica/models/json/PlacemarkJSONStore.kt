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
val placemarkgsonBuilder = GsonBuilder().setPrettyPrinting().create()!!
val placemarklistType = object : TypeToken<ArrayList<PlacemarkModel>>() {}.type!!

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

  override fun findAll(User : Long): List<PlacemarkModel> {
    return placemarks.filter { x -> x.userid == User}
  }

  override fun create(placemark: PlacemarkModel, User: Long) {
    placemark.id = placemarkgenerateRandomId()
    placemark.userid = User
    placemarks.add(placemark)
    serialize()
  }

  override fun update(placemark: PlacemarkModel) {
    val foundPlacemark: PlacemarkModel? = placemarks.find { p -> p.id == placemark.id }
    if (foundPlacemark != null) {
      foundPlacemark.title = placemark.title
      foundPlacemark.description = placemark.description
      foundPlacemark.notes = placemark.notes
      foundPlacemark.visited = placemark.visited
      foundPlacemark.datevisited = placemark.datevisited
      foundPlacemark.lat = placemark.lat
      foundPlacemark.lng = placemark.lng
      foundPlacemark.zoom = placemark.zoom
      if(placemark.images[0] != "") foundPlacemark.images[0] = placemark.images[0]
      if(placemark.images[1] != "") foundPlacemark.images[1] = placemark.images[1]
      if(placemark.images[2] != "") foundPlacemark.images[2] = placemark.images[2]
      if(placemark.images[3] != "") foundPlacemark.images[3] = placemark.images[3]
    }
    serialize()
  }

  override fun delete(placemark: PlacemarkModel) {
    placemarks.remove(placemark)
    serialize()
  }

  override fun findById(id:Long) : PlacemarkModel? {
    return placemarks.find {p -> p.id == id }
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
