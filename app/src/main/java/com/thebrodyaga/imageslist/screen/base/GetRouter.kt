package com.thebrodyaga.imageslist.screen.base

import androidx.fragment.app.Fragment
import com.thebrodyaga.imageslist.app.App
import com.thebrodyaga.imageslist.navigation.RouterTransition

interface GetRouter {

    val fragment: Fragment

    /**
     * Локальный роутер родительского фрагмента, если null то либо не вложен во FlowFragment
     * либо вложен в активити
     */
    fun flowRouter(): RouterTransition? {
        val parentFragment = fragment.parentFragment
        return if (parentFragment is FlowFragment)
            parentFragment.flowRouter
        else null
    }

    /**
     * Ищет первый роутер и так до глобального
     */
    fun anyRouter(): RouterTransition {
        return flowRouter() /*?: getTabRouter()*/ ?: globalRouter()
    }

    /**
     * Глобальный роутер, активити роутер
     */
    fun globalRouter(): RouterTransition {
        return App.appComponent.getRouter()
    }

    /**
     * Идет по родительским фрагментам пока не дойдет до TabContainerFragment
     * Иначе null значит в активити находиться
     *//*
    fun tabRouter(): RouterTransition? {
        var parentFragment: Fragment = fragment.parentFragment ?: return null
        while (true) {
            if (parentFragment is TabContainerFragment) {
                return parentFragment.localRouter
            }
            parentFragment = parentFragment.parentFragment ?: return null
        }
    }*/
}