package com.thebrodyaga.imageslist.screen.base

import android.view.View
import com.thebrodyaga.imageslist.screen.isGone
import com.thebrodyaga.imageslist.screen.isInvisible
import kotlinx.android.synthetic.main.include_error_net.view.*
import kotlinx.android.synthetic.main.include_zero_view.view.*

/**
 * для include_zero_view
 */
class ZeroViewHelper constructor(
    private val view: View,
    private val errorBtnListener: () -> Unit = {},
    private val emptyBtnListener: () -> Unit = {}
) {
    var currentState: ViewState =
        ViewState.GONE
        private set

    init {
        view.error_net_button.setOnClickListener { errorBtnListener.invoke() }
        updateCurrentState(ViewState.GONE)
    }


    private fun updateCurrentState(newState: ViewState) {
        currentState = newState
        with(view) {
            this.isGone(newState == ViewState.GONE)
            when (newState) {
                ViewState.GONE -> {
                    error_net.isInvisible(true)
                    content_progress.isInvisible(true)
                }
                ViewState.EMPTY, ViewState.EMPTY_WITHOUT_BUTTON -> {
                    error_net.isInvisible(true)
                    content_progress.isInvisible(true)
                }
                ViewState.ERROR -> {
                    error_net.isInvisible(false)
                    content_progress.isInvisible(true)
                }
                ViewState.PROGRESS -> {
                    error_net.isInvisible(true)
                    content_progress.isInvisible(false)
                }
            }
        }

    }

    fun showProgress() {
        updateCurrentState(ViewState.PROGRESS)
    }

    fun showEmptyWithoutBtn() {
        updateCurrentState(ViewState.EMPTY_WITHOUT_BUTTON)
    }

    fun showEmpty() {
        updateCurrentState(ViewState.EMPTY)
    }

    fun showError() {
        updateCurrentState(ViewState.ERROR)
    }

    fun hide() {
        updateCurrentState(ViewState.GONE)
    }

    enum class ViewState {
        GONE, EMPTY, EMPTY_WITHOUT_BUTTON, ERROR, PROGRESS
    }
}