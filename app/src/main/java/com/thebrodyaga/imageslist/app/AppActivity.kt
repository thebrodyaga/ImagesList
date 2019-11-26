package com.thebrodyaga.imageslist.app

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.thebrodyaga.imageslist.BuildConfig
import com.thebrodyaga.imageslist.R
import com.thebrodyaga.imageslist.navigation.RouterTransition
import com.thebrodyaga.imageslist.navigation.Screens
import com.thebrodyaga.imageslist.navigation.TransitionNavigator
import com.thebrodyaga.imageslist.screen.base.BaseFragment
import com.thebrodyaga.imageslist.screen.isSystemDarkMode
import moxy.MvpAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class AppActivity : MvpAppCompatActivity() {

    @Inject
    lateinit var router: RouterTransition

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private val navigator: Navigator =
        TransitionNavigator(this, supportFragmentManager, R.id.fragment_container)

    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        isLightSystem(isSystemDarkMode())
        if (BuildConfig.DEBUG)
            supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycle(), true)
        setContentView(R.layout.fragment_flow)
        if (currentFragment == null)
            router.newRootScreen(Screens.ImagesFlowScreen)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val currentFragment = this.currentFragment

        if (currentFragment is BaseFragment)
            currentFragment.onBackPressed()
        else
            super.onBackPressed()
    }

    private fun isLightSystem(isDarkTheme: Boolean?) {
        if (isDarkTheme == null)
            return
        val view = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.systemUiVisibility =
                if (!isDarkTheme)
                    view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                else
                    view.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            view.systemUiVisibility =
                if (!isDarkTheme)
                    view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                else
                    view.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
    }
}
