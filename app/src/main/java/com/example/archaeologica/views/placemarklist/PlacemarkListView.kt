package com.example.archaeologica.views.placemarklist

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.archaeologica.R
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.views.BaseView
import kotlinx.android.synthetic.main.activity_placemark_list.*
import org.jetbrains.anko.toast


class PlacemarkListView :  BaseView(), PlacemarkListener, SearchView.OnQueryTextListener {

  lateinit var presenter: PlacemarkListPresenter
  lateinit var resetSearch: View
  lateinit var menu: Menu

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark_list)
    super.init(toolbarList, false)
    resetSearch = findViewById(R.id.resetSearch)

    presenter = initPresenter(PlacemarkListPresenter(this)) as PlacemarkListPresenter

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    presenter.loadPlacemarks()

    resetSearch.setOnClickListener {

      val view = this.currentFocus
      if (view != null) {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
      }

      invalidateOptionsMenu()
      menuInflater.inflate(R.menu.menu_main, menu)
      toast("Showing all Sites again!")
      resetSearch.isVisible = false
      resetSearch.isClickable = false
      presenter.loadPlacemarks()
    }
  }

  override fun showPlacemarks(placemarks: List<PlacemarkModel>) {
    recyclerView.adapter = PlacemarkAdapter(placemarks, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)

    val searchItem = menu!!.findItem(R.id.item_search)
    val searchView = searchItem.actionView as SearchView
    searchView.setOnQueryTextListener(this)
    searchView.queryHint = "Search Title"
    this.menu = menu

    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> presenter.doAddPlacemark()
      R.id.item_settings -> presenter.doSettings()
      R.id.item_map -> presenter.doMap()
    }
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    return super.onOptionsItemSelected(item)
  }

  override fun onQueryTextSubmit(text: String): Boolean {
    menu.findItem(R.id.item_search).actionView.clearFocus()
    resetSearch.isVisible = true
    resetSearch.isClickable = true
    presenter.searchPlacemarks(text)
    return true
  }

  override fun onQueryTextChange(text: String): Boolean {
    return true
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