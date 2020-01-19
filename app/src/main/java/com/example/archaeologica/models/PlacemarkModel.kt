package com.example.archaeologica.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PlacemarkModel(@PrimaryKey(autoGenerate = true)
                          var id: Long = 0,
                          var userid: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var notes: String = "",
                          var visited: Boolean = false,
                          var datevisited: String = "not visited",
                          var images: MutableList<String> = MutableList(4){""},
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

