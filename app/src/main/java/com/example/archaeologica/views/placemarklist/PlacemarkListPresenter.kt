package com.example.archaeologica.views.placemarklist

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.models.UsersModel
import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW

class PlacemarkListPresenter(view: BaseView) : BasePresenter(view) {

  fun doAddPlacemark() {
    view?.navigateTo(VIEW.PLACEMARK)
  }

  fun doGotoLogin(){
    view?.finish()
  }

  fun doEditPlacemark(placemark: PlacemarkModel) {
    view?.navigateTo(VIEW.PLACEMARK, 0, "placemark_edit", placemark)
  }

  fun loadPlacemarks() {
    doAsync {
      val placemarks = app.placemarks.findAll(view?.intent?.extras?.getParcelable("user")!!)
      uiThread {
        view?.showPlacemarks(placemarks)
      }
    }
  }
}