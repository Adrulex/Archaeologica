package com.example.archaeologica.views.placemark

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri
import com.example.archaeologica.R
import com.example.archaeologica.helpers.checkLocationPermissions
import com.example.archaeologica.helpers.isPermissionGranted
import com.example.archaeologica.models.Location
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*


class PlacemarkPresenter(view: BaseView) : BasePresenter(view) {

  var map: GoogleMap? = null
  var placemark = PlacemarkModel()
  var defaultLocation = Location(49.0208841,12.0693126,13f)
  var edit = false
  var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)

  init {
    if (view.intent.hasExtra("placemark_edit")) {
      edit = true
      placemark = view.intent.extras?.getParcelable("placemark_edit")!!
      view.showPlacemark(placemark)
    } else {
      if (checkLocationPermissions(view)) {
        doSetCurrentLocation()
      }
      view.showPlacemark(placemark)
    }
  }

  @SuppressLint("MissingPermission")
  fun doSetCurrentLocation() {
    locationService.lastLocation.addOnSuccessListener {
      locationUpdate(Location(it.latitude, it.longitude))
    }
  }

  override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    if (isPermissionGranted(requestCode, grantResults)) {
      doSetCurrentLocation()
    } else {
      locationUpdate(defaultLocation)
    }
  }

  fun doConfigureMap(m: GoogleMap) {
    map = m
    locationUpdate(placemark.location)
  }

  fun locationUpdate(location : Location) {
    placemark.location = location
    placemark.location.zoom = 15f
    map?.clear()
    map?.uiSettings?.isZoomControlsEnabled = false
    val options = MarkerOptions().title(placemark.title).position(LatLng(placemark.location.lat, placemark.location.lng))
    map?.addMarker(options)
    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(placemark.location.lat, placemark.location.lng), placemark.location.zoom))
    view?.showLocation(placemark.location)
  }

  fun doUpdateVisited(){
    placemark.visited = !placemark.visited

    var currentDate = "not visited"

    if (placemark.visited) {
      val sdf = SimpleDateFormat("dd.MM.yyyy",Locale.getDefault())
      currentDate = sdf.format(Date())
    }

    placemark.datevisited = currentDate
  }

  fun doUpdateFav(){
    placemark.fav = !placemark.fav
  }

  fun doUpdateRating(rating : Float){
    placemark.rating = rating
  }

  fun doUpdateNotes(notes : String){
    placemark.notes = notes
  }

  fun doAddOrSave(title: String, description: String) {
    placemark.title = title
    placemark.description = description
    doAsync {
      if (edit) {
        app.placemarks.update(placemark)
      } else {
        app.placemarks.create(placemark)
      }
      uiThread {
        view?.finish()
      }
    }
  }

  fun doCancel() {
    view?.finish()
  }

  fun doDelete() {
    app.placemarks.delete(placemark)
    view?.finish()
  }

  fun doSetLocation() {
    view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location",Location(placemark.location.lat, placemark.location.lng, placemark.location.zoom))
  }


  fun doSelectImage(Select : Int, Title : String, Description : String) {
    placemark.title = Title
    placemark.description = Description
    view?.let {
      when (Select) {
        0 -> showImagePicker(view!!, IMAGE1_REQUEST)
        1 -> showImagePicker(view!!, IMAGE2_REQUEST)
        2 -> showImagePicker(view!!, IMAGE3_REQUEST)
        3 -> showImagePicker(view!!, IMAGE4_REQUEST)
      }
    }
  }

  fun showImagePicker(parent: Activity, id: Int) {

    val pickIntent = Intent()
    pickIntent.type = "image/*"
    pickIntent.action = Intent.ACTION_OPEN_DOCUMENT
    pickIntent.addCategory(Intent.CATEGORY_OPENABLE)

    val fileUri : Uri = "".toUri()
    val camIntent = Intent()
    camIntent.type = "image/*"
    camIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
    camIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)

    val chooser = Intent.createChooser(pickIntent, R.string.select_placemark_image.toString())
    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(camIntent))
    parent.startActivityForResult(chooser, id)
  }

  override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

    when (requestCode) {
      IMAGE1_REQUEST -> {
        placemark.images[0] = data.data.toString()
        view?.showPlacemark(placemark)
      }
      IMAGE2_REQUEST -> {
        placemark.images[1] = data.data.toString()
        view?.showPlacemark(placemark)
      }
      IMAGE3_REQUEST -> {
        placemark.images[2] = data.data.toString()
        view?.showPlacemark(placemark)
      }
      IMAGE4_REQUEST -> {
        placemark.images[3] = data.data.toString()
        view?.showPlacemark(placemark)
      }
      LOCATION_REQUEST -> {
        val location = data.extras?.getParcelable<Location>("location")!!
        placemark.location.lat = location.lat
        placemark.location.lng = location.lng
        placemark.location.zoom = location.zoom
        locationUpdate(location)
      }
    }
  }

  fun doShare() {
    val lat = placemark.location.lat
    val lng = placemark.location.lng
    val share = Intent.createChooser(Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT,
        "Hi!\r\nLook at the Site: "+ placemark.title+"\r\nI found it with my Archaeologica-App!\r\n\nhttps://www.google.com/maps/search/?api=1&query="+lat+","+lng
      )
      type="text/plain"
    }, "Share this Site through:")
    view?.startActivity(share)
  }

  fun doRoute(){

    var lat = 0.0
    var lng = 0.0

    locationService.lastLocation.addOnSuccessListener {
      lat = it.latitude
      lng = it.longitude
    }

    val uri = java.lang.String.format(
      Locale.ENGLISH,
      "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)",
      lat,
      lng,
      "Current Location",
      placemark.location.lat,
      placemark.location.lng,
      placemark.title
    )

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    view?.startActivity(intent)
  }
}