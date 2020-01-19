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
    app.activeUser = 0
    view?.finish()
  }

  fun doSettings(){
    view?.navigateTo(VIEW.SETTINGS,0,"user",app.users.findAll().find {i -> i.id == app.activeUser})
  }

  fun doEditPlacemark(placemark: PlacemarkModel) {
    app.activePlacemark = placemark.id
    view?.navigateTo(VIEW.PLACEMARK, 0, "placemark_edit",placemark)
  }

  fun loadPlacemarks() {
    doAsync {
      val placemarks = app.placemarks.findAll(app.activeUser)
      uiThread {
        view?.showPlacemarks(placemarks)
      }
    }
  }
}