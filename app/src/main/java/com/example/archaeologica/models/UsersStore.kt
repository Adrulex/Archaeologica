package com.example.archaeologica.models

interface UsersStore {
  fun findAll(): List<UsersModel>
  fun create(user: UsersModel)
  fun update(user: UsersModel)
  fun delete(user: UsersModel)
  fun findById(id:Long) : UsersModel?
}