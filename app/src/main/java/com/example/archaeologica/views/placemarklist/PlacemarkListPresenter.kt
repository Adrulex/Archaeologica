package com.example.archaeologica.views.placemarklist

import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread


class PlacemarkListPresenter(view: BaseView) : BasePresenter(view) {

  fun doAddPlacemark() {
    view?.navigateTo(VIEW.PLACEMARK)
  }

  fun doGotoLogin(){
    FirebaseAuth.getInstance().signOut()
    view?.navigateTo(VIEW.LOGIN)
  }

  fun doSettings(){
    view?.navigateTo(VIEW.SETTINGS)
  }

  fun doMap(){
    view?.navigateTo(VIEW.MAP)
  }

  fun doEditPlacemark(placemark: PlacemarkModel) {
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

  fun searchPlacemarks(search : String) {
    doAsync {
      val placemarks = app.placemarks.findAll()
      placemarks.filter{p -> p.title.contains(search)}
      uiThread {
        view?.toast("Found " + placemarks.size + " Sites with Title '" + search + "'!")
        view?.showPlacemarks(placemarks)
      }
    }
  }
}