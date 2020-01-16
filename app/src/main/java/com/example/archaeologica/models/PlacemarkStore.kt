package com.example.archaeologica.models

interface PlacemarkStore {
  fun findAll(user : UsersModel): List<PlacemarkModel>
  fun create(placemark: PlacemarkModel)
  fun update(placemark: PlacemarkModel)
  fun delete(placemark: PlacemarkModel)
  fun findById(id:Long) : PlacemarkModel?
}