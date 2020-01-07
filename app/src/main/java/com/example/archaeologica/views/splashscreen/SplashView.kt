package com.example.archaeologica.views.splashscreen

import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.archaeologica.R
import com.example.archaeologica.views.BaseView
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.AnkoLogger


class SplashView : BaseView(), AnkoLogger {

    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter = initPresenter (SplashPresenter(this)) as SplashPresenter

        val animZoomIn: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        SplashImage.startAnimation(animZoomIn)

        Handler().postDelayed({
            presenter.onStartup()
        }, 2000)
    }
}
