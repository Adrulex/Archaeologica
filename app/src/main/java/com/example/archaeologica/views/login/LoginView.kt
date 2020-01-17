package com.example.archaeologica.views.login

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

        Login.setOnClickListener { presenter.doLogin(email.text.toString(),password.text.toString()) }
        Register.setOnClickListener { presenter.doRegister(email.text.toString(),password.text.toString()) }
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

    override fun onReaction(Reaction : String){
        when(Reaction)
        {
            "userTaken" -> toast("User is already registered!")
            "passwordWeak" -> toast("Password is too short!")
            "invalidEmail" -> toast("Please enter a valid email-address!")
            "enterEmail" -> toast("Please enter an email-address!")
            "enterPassword" -> toast("Please enter a password!")
            "wrong" -> toast("Email or Password is wrong!")
            else -> toast("Something went wrong ...")
        }
    }

    override fun onResume() {
        super.onResume()
        password.text.clear()
    }
}
