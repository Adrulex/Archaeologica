package com.example.archaeologica.views.placemark

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RatingBar
import androidx.core.widget.addTextChangedListener
import com.example.archaeologica.R
import com.example.archaeologica.models.Location
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.BaseView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast


class PlacemarkView : BaseView(), AnkoLogger, RatingBar.OnRatingBarChangeListener, ImageListener {

  lateinit var presenter: PlacemarkPresenter
  lateinit var sliderView: SliderView
  var placemark = PlacemarkModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    super.init(toolbarPlacemark, true)
    sliderView = findViewById(R.id.imageSlider)

    presenter = initPresenter (PlacemarkPresenter(this)) as PlacemarkPresenter

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      presenter.doConfigureMap(it)
      it.setOnMapClickListener { presenter.doSetLocation() }
    }

    checkvisited.setOnClickListener {presenter.doUpdateVisited() }
    checkfav.setOnClickListener { presenter.doUpdateFav() }
    notes.addTextChangedListener { presenter.doUpdateNotes(notes.text.toString()) }
    ratingBar.onRatingBarChangeListener = this
  }

  override fun showPlacemark(placemark: PlacemarkModel) {
    placemarkTitle.setText(placemark.title)
    description.setText(placemark.description)
    notes.setText(placemark.notes)
    checkvisited.isChecked = placemark.visited
    checkfav.isChecked = placemark.fav
    ratingBar.rating = placemark.rating
    this.showLocation(placemark.location)

    val adapter = PlacemarkSliderAdapter(placemark,this)
    sliderView.sliderAdapter = adapter
    sliderView.setIndicatorAnimation(IndicatorAnimations.DROP)
    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
    sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
    sliderView.scrollTimeInSec = 4
    sliderView.startAutoCycle()
  }

  @SuppressLint("SetTextI18n")
  override fun showLocation(location: Location) {
    lat.text = "Lat: %.3f".format(location.lat)
    lng.text = "Lng: %.3f".format(location.lng)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_placemark, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onImageClick() {
    presenter.doSelectImage(sliderView.currentPagePosition, placemarkTitle.text.toString(),description.text.toString())
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
      R.id.item_share -> {
        presenter.doShare()
      }
      R.id.item_route -> {
        presenter.doRoute()
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

  override fun onRatingChanged(bar: RatingBar?, rat: Float, user: Boolean) {
    presenter.doUpdateRating(rat)
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
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }
}


