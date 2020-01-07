package com.example.archaeologica.views.splashscreen

import android.os.Bundle
import android.os.Handler
import com.example.archaeologica.R
import com.example.archaeologica.views.BaseView
import org.jetbrains.anko.AnkoLogger

class SplashView : BaseView(), AnkoLogger {

    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter = initPresenter (SplashPresenter(this)) as SplashPresenter

        Handler().postDelayed({
            presenter.onStartup()
        }, 2000)
    }
}
