package com.thebrodyaga.imageslist.screen.base

import com.thebrodyaga.imageslist.navigation.LocalCiceroneHolder
import com.thebrodyaga.imageslist.navigation.RouterTransition
import com.thebrodyaga.imageslist.navigation.TransitionNavigator
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator

/**
 * фрагмент контейнер для вложенной навигации, тут храниться локальный роутер,
 * тут скоупы открыать и закрывать
 */
abstract class FlowFragment : BaseFragment() {

    private val navigator: Navigator by lazy {
        object : TransitionNavigator(requireActivity(), childFragmentManager, getContainerId()) {
            /**
             * корректный выход из FlowFragment иначе активити финишит
             */
            override fun fragmentBack() {
                if (childFragmentManager.backStackEntryCount > 0)
                    super.fragmentBack()
                else anyRouter().exit()
            }
        }
    }

    protected val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(getContainerId()) as? BaseFragment

    protected abstract fun getContainerId(): Int
    protected abstract fun getContainerName(): String
    protected abstract fun getCiceroneHolder(): LocalCiceroneHolder

    val flowRouter: RouterTransition get() = getCicerone().router

    private fun getCicerone(): Cicerone<RouterTransition> =
        getCiceroneHolder().getCicerone(getContainerName())

    override fun onResume() {
        super.onResume()
        getCicerone().navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        getCicerone().navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val currentFragment =
            childFragmentManager.findFragmentById(getContainerId()) as? BaseFragment
        currentFragment?.onBackPressed()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        currentFragment?.apply { onHiddenChanged(hidden) }
    }
}