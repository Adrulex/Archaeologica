package com.example.archaeologica.views.placemarklist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_placemark_list.*
import com.example.archaeologica.R
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.BaseView
import org.jetbrains.anko.toast

class PlacemarkListView :  BaseView(), PlacemarkListener {

  lateinit var presenter: PlacemarkListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark_list)
    super.init(toolbarList, false)

    presenter = initPresenter(PlacemarkListPresenter(this)) as PlacemarkListPresenter

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    presenter.loadPlacemarks()
  }

  override fun showPlacemarks(placemarks: List<PlacemarkModel>) {
    recyclerView.adapter = PlacemarkAdapter(placemarks, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> presenter.doAddPlacemark()
      R.id.item_settings -> presenter.doSettings()
      R.id.item_map -> presenter.doMap()
      R.id.item_search -> presenter.doSearch()
    }
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    return super.onOptionsItemSelected(item)
  }

  override fun onPlacemarkClick(placemark: PlacemarkModel) {
    presenter.doEditPlacemark(placemark)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadPlacemarks()
    super.onActivityResult(requestCode, resultCode, data)
  }

  override fun onBackPressed() {

    val builder = AlertDialog.Builder(this)
    builder.setTitle("Logout?")
      .setMessage("Are you sure you want to log out?")
      .setPositiveButton("Yes"){ _, _ ->
        toast("Logout successful!")
        presenter.doGotoLogin()
      }
      .setNeutralButton("Cancel"){_,_ -> }
    val dialog: AlertDialog = builder.create()
    dialog.show()
  }

}