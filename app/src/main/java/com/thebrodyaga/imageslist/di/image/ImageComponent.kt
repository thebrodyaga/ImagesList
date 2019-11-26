package com.thebrodyaga.imageslist.di.image

import com.thebrodyaga.imageslist.di.FirstScope
import com.thebrodyaga.imageslist.screen.image.flow.ImageFlowFragment
import com.thebrodyaga.imageslist.screen.image.fullscreen.ImageFullscreenFragment
import com.thebrodyaga.imageslist.screen.image.list.ImageListFragment
import dagger.Subcomponent

@FirstScope
@Subcomponent(modules = [ImageModule::class])
interface ImageComponent {

    fun inject(imageFlowFragment: ImageFlowFragment)
    fun inject(imageListFragment: ImageListFragment)
    fun inject(imageFullscreenFragment: ImageFullscreenFragment)
}