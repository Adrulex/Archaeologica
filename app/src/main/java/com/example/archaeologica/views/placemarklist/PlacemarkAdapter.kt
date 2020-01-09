package com.example.archaeologica.views.placemarklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_placemark.view.*
import com.example.archaeologica.R
import com.example.archaeologica.helpers.readImageFromPath
import com.example.archaeologica.models.PlacemarkModel

interface PlacemarkListener {
  fun onPlacemarkClick(placemark: PlacemarkModel)
}

class PlacemarkAdapter constructor(private var placemarks: List<PlacemarkModel>,
                                   private val listener: PlacemarkListener
) : RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

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
  }

  override fun getItemCount(): Int = placemarks.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(placemark: PlacemarkModel, listener: PlacemarkListener) {
      itemView.placemarkTitle.text = placemark.title
      itemView.description.text = "Lat: " + "%.6f".format(placemark.lat) + " \nLng: " + "%.6f".format(placemark.lng)
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, placemark.image1))
      itemView.setOnClickListener { listener.onPlacemarkClick(placemark) }
        itemView.checkBox.isChecked = placemark.visited
    }
  }
}