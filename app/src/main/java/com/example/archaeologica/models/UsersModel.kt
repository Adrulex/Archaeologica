package com.example.archaeologica.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class UsersModel(@PrimaryKey(autoGenerate = true)
                          var id: Long = 0,
                          var name: String = "",
                          var email: String = ""): Parcelable
