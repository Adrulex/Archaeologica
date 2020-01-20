package com.example.archaeologica.views.settings

import android.content.SharedPreferences
import androidx.preference.PreferenceManager.*
import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast

class SettingsPresenter(view: BaseView) : BasePresenter(view) {

    init {
        val prefs: SharedPreferences = getDefaultSharedPreferences(view.applicationContext)
        val edit = prefs.edit()
        edit.putString("email", FirebaseAuth.getInstance().currentUser!!.email)
        edit.putString("password", "••••••••")
        edit.putString("sites", app.placemarks.findAll().size.toString())
        edit.putString("visited", app.placemarks.findAll().filter { p -> p.visited }.size.toString())
        edit.apply()
    }

    fun doSave() {
        val prefs: SharedPreferences = getDefaultSharedPreferences(view?.applicationContext)
        val fire = FirebaseAuth.getInstance()
        val user = fire.currentUser
        var error = false

        user?.updateEmail(prefs.getString("email", "")!!)?.addOnCompleteListener(view!!) { task ->
            if (!task.isSuccessful) {
                view?.toast("Failed: ${task.exception?.message}")
                error = true
            }
        }

        user?.updatePassword(prefs.getString("password", "")!!)?.addOnCompleteListener(view!!) { task ->
            if (!task.isSuccessful) {
                view?.toast("Failed: ${task.exception?.message}")
                error = true
            }
        }

        if(!error){
            view?.onSave()
        }
    }
}


