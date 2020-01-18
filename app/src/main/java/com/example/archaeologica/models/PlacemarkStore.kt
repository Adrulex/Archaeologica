package com.example.archaeologica.models

interface PlacemarkStore {
  fun findAll(User : Long): List<PlacemarkModel>
  fun create(placemark: PlacemarkModel, User : Long)
  fun update(placemark: PlacemarkModel)
  fun delete(placemark: PlacemarkModel)
  fun findById(id:Long) : PlacemarkModel?
}