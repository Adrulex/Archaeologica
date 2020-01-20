package com.example.archaeologica.views.splashscreen

import com.example.archaeologica.views.BasePresenter
import com.example.archaeologica.views.BaseView
import com.example.archaeologica.views.VIEW

class SplashPresenter(view: BaseView) : BasePresenter(view) {

    init{ /* do nothing */ }

    fun onStartup(){
        view?.navigateTo(VIEW.LOGIN)
        view?.finish()
    }

}
