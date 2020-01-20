package com.example.archaeologica.models

interface PlacemarkStore {
  fun findAll(): List<PlacemarkModel>
  fun create(placemark: PlacemarkModel)
  fun update(placemark: PlacemarkModel)
  fun delete(placemark: PlacemarkModel)
  fun clear()
  fun findById(id:Long) : PlacemarkModel?
}