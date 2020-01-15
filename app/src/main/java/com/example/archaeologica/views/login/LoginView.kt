package com.example.archaeologica.views.login

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import com.example.archaeologica.R
import com.example.archaeologica.views.BaseView
import org.jetbrains.anko.AnkoLogger
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast


class LoginView : BaseView(), AnkoLogger {

    lateinit var presenter: LoginPresenter
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        super.init(toolbar, false)

        presenter = initPresenter (LoginPresenter(this)) as LoginPresenter

        Login.setOnClickListener { presenter.doLogin() }
        Register.setOnClickListener { presenter.doRegister() }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        toast("Please click BACK again to exit")
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
