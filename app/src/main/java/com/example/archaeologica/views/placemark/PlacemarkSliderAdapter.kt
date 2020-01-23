package com.example.archaeologica.views.placemark

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.archaeologica.R
import com.example.archaeologica.models.PlacemarkModel
import com.smarteist.autoimageslider.SliderViewAdapter

interface ImageListener {
    fun onImageClick()
}

class PlacemarkSliderAdapter(private var placemark: PlacemarkModel, private val listener: ImageListener) : SliderViewAdapter<PlacemarkSliderAdapter.SliderAdapterVH>() {
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        Glide.with(viewHolder.itemView)
            .load( placemark.images[position])
            .into(viewHolder.imageView)

        viewHolder.bind(listener)
    }

    override fun getCount(): Int { //slider view count could be dynamic size
        return placemark.images.size
    }

    class SliderAdapterVH(var itemView: View) :
        ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById((R.id.iv_auto_image_slider))

        fun bind(listener: ImageListener){
            itemView.setOnClickListener{listener.onImageClick()}
        }
    }

}