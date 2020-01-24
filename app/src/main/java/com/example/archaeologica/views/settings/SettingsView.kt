package com.example.archaeologica.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.preference.PreferenceFragmentCompat
import com.example.archaeologica.R
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.settings_activity.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast


class SettingsView : BaseView(), AnkoLogger {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        super.init(toolbarSettings, true)

        presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_save -> {presenter.doSave()}
        }
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        return super.onOptionsItemSelected(item)
    }

    override fun onSave(){
        toast("Please re-authenticate with new credentials!")
        FirebaseAuth.getInstance().signOut()
        navigateTo(VIEW.LOGIN)
    }
}