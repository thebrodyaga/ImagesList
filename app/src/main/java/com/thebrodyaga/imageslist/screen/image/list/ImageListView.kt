package com.thebrodyaga.imageslist.screen.image.list

import com.thebrodyaga.imageslist.screen.base.Paginator
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ImageListView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun renderPaginatorState(state: Paginator.State)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(error: Throwable)
}