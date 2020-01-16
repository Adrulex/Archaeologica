package com.example.archaeologica.views.placemark

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import com.example.archaeologica.R
import com.example.archaeologica.helpers.readImageFromPath
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.*
import kotlinx.android.synthetic.main.activity_placemark.description
import kotlinx.android.synthetic.main.activity_placemark.placemarkTitle
import kotlinx.android.synthetic.main.card_placemark.*
import java.util.*

class PlacemarkView : BaseView(), AnkoLogger {

  lateinit var presenter: PlacemarkPresenter
  var placemark = PlacemarkModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    super.init(toolbarAdd, true)

    presenter = initPresenter (PlacemarkPresenter(this)) as PlacemarkPresenter

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      presenter.doConfigureMap(it)
      it.setOnMapClickListener { presenter.doSetLocation() }
    }

    placemarkImage1.setOnClickListener { presenter.doSelectImage(IMAGE1_REQUEST, placemarkTitle.text.toString(),description.text.toString()) }
    placemarkImage2.setOnClickListener { presenter.doSelectImage(IMAGE2_REQUEST, placemarkTitle.text.toString(),description.text.toString()) }
    placemarkImage3.setOnClickListener { presenter.doSelectImage(IMAGE3_REQUEST, placemarkTitle.text.toString(),description.text.toString()) }
    placemarkImage4.setOnClickListener { presenter.doSelectImage(IMAGE4_REQUEST, placemarkTitle.text.toString(),description.text.toString()) }
    checkvisited.setOnClickListener {presenter.doUpdateVisited() }
  }

  override fun showPlacemark(placemark: PlacemarkModel) {
    placemarkTitle.setText(placemark.title)
    description.setText(placemark.description)
    checkvisited.isChecked = placemark.visited
    if(placemark.image1 != "") placemarkImage1.setImageBitmap(readImageFromPath(this, placemark.image1))
    if(placemark.image2 != "") placemarkImage2.setImageBitmap(readImageFromPath(this, placemark.image2))
    if(placemark.image3 != "") placemarkImage3.setImageBitmap(readImageFromPath(this, placemark.image3))
    if(placemark.image4 != "") placemarkImage4.setImageBitmap(readImageFromPath(this, placemark.image4))
    this.showLocation(placemark.lat, placemark.lng)
  }

  @SuppressLint("SetTextI18n")
  override fun showLocation(latitude : Double, longitude : Double) {
    lat.text = "Lat: %.3f".format(latitude)
    lng.text = "Lng: %.3f".format(longitude)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_placemark, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        presenter.doDelete()
      }
      R.id.item_save -> {
        if (placemarkTitle.text.toString().isEmpty()) {
          toast(R.string.enter_placemark_title)
        } else {
          presenter.doAddOrSave(placemarkTitle.text.toString(), description.text.toString())
        }
      }
    }
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      presenter.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onBackPressed() {
    presenter.doCancel()
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
    presenter.doResartLocationUpdates()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }
}


