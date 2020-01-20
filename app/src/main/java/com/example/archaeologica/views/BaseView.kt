package com.example.archaeologica.views

import android.content.Intent

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.archaeologica.models.Location
import org.jetbrains.anko.AnkoLogger
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.editlocation.EditLocationView
import com.example.archaeologica.views.login.LoginView
import com.example.archaeologica.views.map.PlacemarkMapView
import com.example.archaeologica.views.placemark.PlacemarkView
import com.example.archaeologica.views.placemarklist.PlacemarkListView
import com.example.archaeologica.views.settings.SettingsView

const val IMAGE1_REQUEST = 0
const val IMAGE2_REQUEST = 1
const val IMAGE3_REQUEST = 2
const val IMAGE4_REQUEST = 3
const val LOCATION_REQUEST = 4

enum class VIEW {
  LOCATION, PLACEMARK, LIST, SETTINGS, LOGIN, MAP, NAVIGATOR
}

abstract class BaseView : AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null

  fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    var intent = Intent(this, PlacemarkListView::class.java)
    when (view) {
      VIEW.SETTINGS -> intent = Intent(this, SettingsView::class.java)
      VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
      VIEW.PLACEMARK -> intent = Intent(this, PlacemarkView::class.java)
      VIEW.LIST -> intent = Intent(this, PlacemarkListView::class.java)
      VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
      VIEW.MAP -> intent = Intent(this, PlacemarkMapView::class.java)
      VIEW.NAVIGATOR -> {}
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
  open fun showLocation(location: Location) {}
  open fun onSave() {}
  open fun showProgress() {}
  open fun hideProgress() {}
}