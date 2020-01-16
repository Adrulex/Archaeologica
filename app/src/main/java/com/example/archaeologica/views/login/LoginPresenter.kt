package com.example.archaeologica.views.login

import com.example.archaeologica.models.UsersModel
import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var user = UsersModel()

    fun doLogin(email:String,password:String){

        if (!email.contains('@')) view?.onReaction("invalidEmail")
        else{
            if (searchforuser(email)) view?.onReaction("userTaken")
            else{
                if (password.length<8) view?.onReaction("passwordWeak")
                else{
                    user.email=email
                    user.password=password
                    app.users.create(user)
                    view?.navigateTo(VIEW.LIST, 0, "user", user)
                }
            }
        }
    }

    fun doRegister(email:String,password:String){

        if (!email.contains('@')) view?.onReaction("invalidEmail")
        else{
            if (searchforuser(email)) view?.onReaction("userTaken")
            else{
                if (password.length<8) view?.onReaction("passwordWeak")
                else{
                    user.email=email
                    user.password=password
                    app.users.create(user)
                    view?.navigateTo(VIEW.LIST, 0, "user", user)
                }
            }
        }
    }

    fun searchforuser(email : String) : Boolean {
        val allUsers = mutableListOf<UsersModel>()
        allUsers.filter {it.email == email}
        return allUsers.any()
    }
}
