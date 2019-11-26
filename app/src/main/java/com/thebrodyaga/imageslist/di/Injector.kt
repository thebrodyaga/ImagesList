package com.thebrodyaga.imageslist.di

import com.thebrodyaga.imageslist.app.App
import com.thebrodyaga.imageslist.di.image.ImageComponent

object Injector {

    private var driverComponent: ImageComponent? = null
    fun plusImageComponent(): ImageComponent {
        if (driverComponent == null)
            driverComponent = App.appComponent.plusImageComponent()
        return driverComponent as ImageComponent
    }

    fun clearImageComponent() {
        driverComponent = null
    }
}