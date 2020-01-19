package com.example.archaeologica.views.settings

import android.content.SharedPreferences
import androidx.preference.PreferenceManager.*
import com.example.archaeologica.models.UsersModel
import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView

class SettingsPresenter(view: BaseView) : BasePresenter(view) {

    var user: UsersModel = view.intent.extras?.getParcelable("user")!!

    init {
        val prefs: SharedPreferences = getDefaultSharedPreferences(view.applicationContext)
        val edit = prefs.edit()
        edit.putString("email", user.email)
        edit.putString("password", user.password)
        edit.putString("sites", app.placemarks.findAll(user.id).size.toString())
        edit.putString(
            "visited",
            app.placemarks.findAll(user.id).filter { p -> p.visited }.size.toString()
        )
        edit.apply()
    }

    fun doSave() {
        val prefs: SharedPreferences = getDefaultSharedPreferences(view?.applicationContext)
        val email = prefs.getString("email", app.users.findById(user.id)?.email)!!
        val password = prefs.getString("password", app.users.findById(user.id)?.password)!!

        if (!email.contains('@')) view?.onError("invalidEmail")
        else {
            if (searchforuser(email)) view?.onError("userTaken")
            else {
                if (password.length < 8) view?.onError("passwordWeak")
                else {
                    app.users.findById(user.id)?.email = prefs.getString("email", app.users.findById(user.id)?.email)!!
                    app.users.findById(user.id)?.password = prefs.getString("password", app.users.findById(user.id)?.password)!!

                    view?.onSave()
                }
            }
        }
    }

    fun searchforuser(email: String): Boolean {
        val allUsers = app.users.findAll()
        return allUsers.any { x -> x.email == email }
    }
}


