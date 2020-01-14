package com.example.archaeologica.views.editlocation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_edit_location.*
import com.example.archaeologica.R
import com.example.archaeologica.views.BaseView

class EditLocationView : BaseView(), GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

  lateinit var presenter: EditLocationPresenter

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_location)
    super.init(toolbar, true)

    presenter = initPresenter(EditLocationPresenter(this)) as EditLocationPresenter

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      it.setOnMarkerDragListener(this)
      it.setOnMarkerClickListener(this)
      presenter.doConfigureMap(it)
    }

    lat.text = "Lat\n" + "%.6f".format(presenter.location.lat)
    lng.text = "Lng\n" + "%.6f".format(presenter.location.lng)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_edit_location, menu)
    return super.onCreateOptionsMenu(menu)
  }

  @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_save -> {
        presenter.doSave()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onMarkerDragStart(marker: Marker) {}

  @SuppressLint("SetTextI18n")
  override fun onMarkerDrag(marker: Marker) {
    lat.text = "Lat\n" + "%.6f".format(marker.position.latitude)
    lng.text = "Lng\n" + "%.6f".format(marker.position.longitude)
  }

  override fun onMarkerDragEnd(marker: Marker) {
    presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude)
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    presenter.doUpdateMarker(marker)
    return false
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }
}