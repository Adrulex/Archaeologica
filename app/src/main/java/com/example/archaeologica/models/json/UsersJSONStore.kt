package com.example.archaeologica.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import com.example.archaeologica.helpers.*
import com.example.archaeologica.models.UsersModel
import com.example.archaeologica.models.UsersStore
import java.util.*

val USERS_JSON_FILE = "users.json"
val usersgsonBuilder = GsonBuilder().setPrettyPrinting().create()!!
val userslistType = object : TypeToken<ArrayList<UsersModel>>() {}.type!!

fun usersgenerateRandomId(): Long {
  return Random().nextLong()
}

class UsersJSONStore(val context: Context) : UsersStore, AnkoLogger {

  var users = mutableListOf<UsersModel>()

  init {
    if (exists(context, USERS_JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): List<UsersModel> {
    return users
  }

  override fun create(user: UsersModel) {
    user.id = usersgenerateRandomId()
    users.add(user)
    serialize()
  }

  override fun update(user: UsersModel) {
    val usersList = findAll() as ArrayList<UsersModel>
    val founduser: UsersModel? = usersList.find { p -> p.id == user.id }
    if (founduser != null) {
      founduser.password = user.password
      founduser.email = user.email
    }
    serialize()
  }

  override fun delete(user: UsersModel) {
    users.remove(user)
    serialize()
  }

  override fun findById(id:Long) : UsersModel? {
    return users.find {u -> u.id == id }
  }

  private fun serialize() {
    val jsonString = usersgsonBuilder.toJson(users,
      userslistType
    )
    write(context, USERS_JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, USERS_JSON_FILE)
    users = Gson().fromJson(jsonString, userslistType)
  }
}
