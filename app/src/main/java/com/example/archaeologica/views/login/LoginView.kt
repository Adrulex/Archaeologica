package com.example.archaeologica.views.login

import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import com.example.archaeologica.R
import com.example.archaeologica.views.BaseView

class LoginView : BaseView() {

  lateinit var presenter: LoginPresenter
  private var doubleBackToExitPressedOnce = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    progressBar.visibility = View.GONE
    init(toolbar, false)

    presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

    signUp.setOnClickListener {
      val email = email.text.toString()
      val password = password.text.toString()
      if (email == "" || password == "") {
        toast("Please provide email + password")
      }
      else {
        presenter.doSignUp(email,password)
      }
    }

    logIn.setOnClickListener {
      val email = email.text.toString()
      val password = password.text.toString()
      if (email == "" || password == "") {
        toast("Please provide email + password")
      }
      else {
        presenter.doLogin(email,password)
      }
    }
  }

  override fun showProgress() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progressBar.visibility = View.GONE
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