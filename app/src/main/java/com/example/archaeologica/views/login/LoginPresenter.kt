package com.example.archaeologica.views.login

import com.example.archaeologica.models.UsersModel
import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    fun doLogin(email:String,password:String){
        if (!email.contains('@')) view?.onReaction("invalidEmail")
        else{
            if (!searchforuser(email)) view?.onReaction("wrong")
            else {
                val allUsers = app.users.findAll()
                val current = allUsers.find {x -> x.email == email}
                if(current?.password != password) view?.onReaction("wrong")
                else{
                    app.activeUser = current.id
                    view?.navigateTo(VIEW.LIST)
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
                    val user = UsersModel()
                    user.email=email
                    user.password=password
                    app.users.create(user)
                    val allUsers = app.users.findAll()
                    val current = allUsers.find {x -> x.email == email}
                    app.activeUser = current?.id!!
                    view?.navigateTo(VIEW.LIST)
                }
            }
        }
    }

    fun searchforuser(email : String) : Boolean {
        val allUsers = app.users.findAll()
        return allUsers.any{x -> x.email == email}
    }
}
