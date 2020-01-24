package com.example.archaeologica.views.placemarklist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_placemark.view.*
import com.example.archaeologica.R
import com.example.archaeologica.models.PlacemarkModel

interface PlacemarkListener {
  fun onPlacemarkClick(placemark: PlacemarkModel)
}

class PlacemarkAdapter constructor(private var placemarks: List<PlacemarkModel>, private val listener: PlacemarkListener) : RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.card_placemark,
            parent,
            false
        )
    )
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val placemark = placemarks[holder.adapterPosition]
    holder.bind(placemark, listener)
    Glide.with(holder.itemView)
      .load(placemark.images[0])
      .into(holder.itemView.imageIcon)
  }

  override fun getItemCount(): Int = placemarks.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun bind(placemark: PlacemarkModel, listener: PlacemarkListener) {
      itemView.placemarkTitle.text = placemark.title
      itemView.latlng.text = "Lat: " + "%.3f".format(placemark.location.lat) + "   |   Lng: " + "%.3f".format(placemark.location.lng)
      itemView.setOnClickListener { listener.onPlacemarkClick(placemark) }
        itemView.checkBox.isChecked = placemark.visited
        itemView.checkFav.isChecked = placemark.fav
        itemView.datevisited.text = placemark.datevisited
    }
  }
}