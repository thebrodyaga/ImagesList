package com.thebrodyaga.imageslist.di

import android.app.Application
import com.google.gson.Gson
import com.thebrodyaga.imageslist.di.modules.AppModule
import com.thebrodyaga.imageslist.app.AppActivity
import com.thebrodyaga.imageslist.di.image.ImageComponent
import com.thebrodyaga.imageslist.di.modules.NavigationModule
import com.thebrodyaga.imageslist.di.modules.NetworkModule
import com.thebrodyaga.imageslist.navigation.RouterTransition
import dagger.BindsInstance
import dagger.Component
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class, NetworkModule::class])
interface AppComponent {
    fun getRouter(): RouterTransition
    fun inject(activity: AppActivity)
    fun plusImageComponent(): ImageComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}