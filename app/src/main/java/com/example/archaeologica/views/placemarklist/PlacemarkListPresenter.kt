package com.example.archaeologica.views.placemarklist

import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.util.*


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
      val temp = placemarks.filter{p -> p.title.toLowerCase(Locale.ENGLISH).contains(search.toLowerCase(Locale.ENGLISH))}
      uiThread {
        view?.toast("Found " + temp.size + " Sites containing '" + search + "' in Title!")
        view?.showPlacemarks(temp)
      }
    }
  }
}