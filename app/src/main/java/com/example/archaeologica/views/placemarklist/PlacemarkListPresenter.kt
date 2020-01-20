package com.example.archaeologica.views.placemarklist

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW
import com.google.firebase.auth.FirebaseAuth

class PlacemarkListPresenter(view: BaseView) : BasePresenter(view) {

  fun doAddPlacemark() {
    view?.navigateTo(VIEW.PLACEMARK)
  }

  fun doGotoLogin(){
    FirebaseAuth.getInstance().signOut()
    view?.finish()
  }

  fun doSettings(){
    view?.navigateTo(VIEW.SETTINGS)
  }

  fun doEditPlacemark(placemark: PlacemarkModel) {
    app.activePlacemark = placemark.id
    view?.navigateTo(VIEW.PLACEMARK, 0, "placemark_edit",placemark)
  }

  fun loadPlacemarks() {
    doAsync {
      val placemarks = app.placemarks.findAll()
      uiThread {
        view?.showPlacemarks(placemarks)
      }
    }
  }
}