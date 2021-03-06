package com.example.archaeologica.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.example.archaeologica.models.PlacemarkModel
import com.example.archaeologica.models.PlacemarkStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

class PlacemarkFireStore(val context: Context) : PlacemarkStore, AnkoLogger {

  val placemarks = ArrayList<PlacemarkModel>()
  lateinit var userId: String
  lateinit var db: DatabaseReference
  lateinit var st: StorageReference

  override fun findAll(): List<PlacemarkModel> {
    return placemarks
  }

  override fun findById(id: Long): PlacemarkModel? {
    return placemarks.find { p -> p.id == id }
  }

  override fun create(placemark: PlacemarkModel) {
    val key = db.child("users").child(userId).child("placemarks").push().key
    key?.let {
      placemark.fbId = key
      placemarks.add(placemark)
      db.child("users").child(userId).child("placemarks").child(key).setValue(placemark)
      for(i in 0..3) {
        updateImage(placemark,i)
      }
    }
  }

  override fun update(placemark: PlacemarkModel) {
    val foundPlacemark: PlacemarkModel? = placemarks.find { p -> p.fbId == placemark.fbId }
    if (foundPlacemark != null) {
      foundPlacemark.title = placemark.title
      foundPlacemark.description = placemark.description
      foundPlacemark.notes = placemark.notes
      foundPlacemark.images = placemark.images
      foundPlacemark.datevisited = placemark.datevisited
      foundPlacemark.visited = placemark.visited
      foundPlacemark.fav = placemark.fav
      foundPlacemark.rating = placemark.rating
      foundPlacemark.location = placemark.location
    }

    db.child("users").child(userId).child("placemarks").child(placemark.fbId).setValue(placemark)
    for(i in 0..3){
      if ((placemark.images[i].length) > 0 && (placemark.images[i][0] != 'h')) {
      updateImage(placemark,i)
      }
    }
  }

  override fun delete(placemark: PlacemarkModel) {
    db.child("users").child(userId).child("placemarks").child(placemark.fbId).removeValue()
    placemarks.remove(placemark)
  }

  override fun clear() {
    placemarks.clear()
  }

  fun updateImage(placemark: PlacemarkModel, index : Int) {
    if (placemark.images[index] != "") {
      val fileName = File(placemark.images[index])
      val imageName = fileName.name
      val imageRef = st.child("$userId/$imageName")
      val baos = ByteArrayOutputStream()
      val bitmap = Glide.with(context).asBitmap().load(placemark.images[index]).submit(500,500).get()

      bitmap.let {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
          println(it.message)
        }.addOnSuccessListener { taskSnapshot ->
          taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
            placemark.images[index] = it.toString()
            db.child("users").child(userId).child("placemarks").child(placemark.fbId).setValue(placemark)
          }
        }
      }
    }
  }

  fun fetchPlacemarks(placemarksReady: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(dataSnapshot: DatabaseError) {
      }
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.mapNotNullTo(placemarks) { it.getValue<PlacemarkModel>(PlacemarkModel::class.java) }
        placemarksReady()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    st = FirebaseStorage.getInstance().reference
    placemarks.clear()
    db.child("users").child(userId).child("placemarks").addListenerForSingleValueEvent(valueEventListener)
  }
}