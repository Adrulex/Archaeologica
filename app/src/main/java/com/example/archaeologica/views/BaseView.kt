package com.example.archaeologica.views

import android.content.Intent

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.AnkoLogger

import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.editlocation.EditLocationView
import com.example.archaeologica.views.placemark.PlacemarkView
import com.example.archaeologica.views.placemarklist.PlacemarkListView

val IMAGE1_REQUEST = 1
val IMAGE2_REQUEST = 2
val IMAGE3_REQUEST = 3
val IMAGE4_REQUEST = 4
val LOCATION_REQUEST = 5

enum class VIEW {
  LOCATION, PLACEMARK, LIST
}

abstract class BaseView : AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null

  fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    var intent = Intent(this, PlacemarkListView::class.java)
    when (view) {
      VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
      VIEW.PLACEMARK -> intent = Intent(this, PlacemarkView::class.java)
      VIEW.LIST -> intent = Intent(this, PlacemarkListView::class.java)
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter {
    basePresenter = presenter
    return presenter
  }

  fun init(toolbar: Toolbar, upEnabled: Boolean) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showPlacemark(placemark: PlacemarkModel) {}
  open fun showPlacemarks(placemarks: List<PlacemarkModel>) {}
  open fun showLocation(latitude : Double, longitude : Double) {}
  open fun showProgress() {}
  open fun hideProgress() {}
}