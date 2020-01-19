package com.example.archaeologica.views.placemark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.archaeologica.R
import com.example.archaeologica.models.PlacemarkModel
import com.smarteist.autoimageslider.SliderViewAdapter


class PlacemarkSliderAdapter(context: Context, private var placemark: PlacemarkModel) : SliderViewAdapter<PlacemarkSliderAdapter.SliderAdapterVH>() {
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
            Glide.with(viewHolder.itemView)
                .load( placemark.images[position])
                .into(viewHolder.imageView)
    }

    override fun getCount(): Int { //slider view count could be dynamic size
        return placemark.images.size
    }

    class SliderAdapterVH(itemView: View) :
        ViewHolder(itemView) {
        var itemView: View
        var imageView: ImageView

        init {
            imageView = itemView.findViewById((R.id.iv_auto_image_slider))
            this.itemView = itemView
        }
    }

    init {
        this.context = context
    }
}