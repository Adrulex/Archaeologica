package com.example.archaeologica.views.login

import com.example.archaeologica.models.UsersModel
import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var users = UsersModel()

    fun doLogin(){
        view?.navigateTo(VIEW.LIST)
    }

    fun doRegister(){
        view?.navigateTo(VIEW.LIST)
    }
}
