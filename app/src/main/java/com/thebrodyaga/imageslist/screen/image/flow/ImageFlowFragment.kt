package com.thebrodyaga.imageslist.screen.image.flow


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.thebrodyaga.imageslist.R
import com.thebrodyaga.imageslist.di.Injector
import com.thebrodyaga.imageslist.navigation.LocalCiceroneHolder
import com.thebrodyaga.imageslist.navigation.Screens
import com.thebrodyaga.imageslist.screen.base.FlowFragment
import javax.inject.Inject

class ImageFlowFragment : FlowFragment() {

    @Inject
    lateinit var localCiceroneHolder: LocalCiceroneHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.plusImageComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun getContainerId(): Int = R.id.fragment_container

    override fun getContainerName(): String = "ImageFlowFragment"

    override fun getCiceroneHolder(): LocalCiceroneHolder = localCiceroneHolder

    override fun getLayoutId(): Int = R.layout.fragment_flow

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (currentFragment == null)
            flowRouter.newRootScreen(Screens.ImagesFlowScreen.ImagesListScreen)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (activity?.isFinishing == true)
            Injector.clearImageComponent()
    }
}
