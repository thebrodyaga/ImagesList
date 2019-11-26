package com.thebrodyaga.imageslist.navigation

import androidx.fragment.app.Fragment
import com.thebrodyaga.imageslist.screen.image.flow.ImageFlowFragment
import com.thebrodyaga.imageslist.screen.image.fullscreen.ImageFullscreenFragment
import com.thebrodyaga.imageslist.screen.image.list.ImageListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object ImagesFlowScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ImageFlowFragment()
        }

        object ImagesListScreen : SupportAppScreen() {
            override fun getFragment(): Fragment {
                return ImageListFragment()
            }
        }

        data class ImageFullscreenScreen(val id: String) : SupportAppScreen() {
            override fun getFragment(): Fragment {
                return ImageFullscreenFragment.newInstance(id)
            }
        }
    }
}