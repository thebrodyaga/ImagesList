package com.thebrodyaga.imageslist.app

import android.app.Application
import com.thebrodyaga.imageslist.di.AppComponent
import com.thebrodyaga.imageslist.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}