package com.thebrodyaga.imageslist.screen

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.forEach

fun View.defaultTopMarginWindowInsert() {
    setOnApplyWindowInsetsListener { v, insets ->
        val params = v.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = insets.systemWindowInsetTop
        v.layoutParams = params
        insets
    }
}

fun View.isInvisible(isInvisible: Boolean) {
    this.visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.isGone(isInvisible: Boolean) {
    this.visibility = if (isInvisible) View.GONE else View.VISIBLE
}

fun View.defaultBottomMarginWindowInsert() {
    setOnApplyWindowInsetsListener { v, insets ->
        val params = v.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = insets.systemWindowInsetBottom
        v.layoutParams = params
        insets
    }
}

fun ViewGroup.defaultOnApplyWindowInsetsListener() {
    setOnApplyWindowInsetsListener { _, insets ->
        forEach { it.dispatchApplyWindowInsets(insets) }
        insets
    }
}

fun Context.isSystemDarkMode(): Boolean? {
    return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        // Night mode is not active, we're using the light theme
        Configuration.UI_MODE_NIGHT_NO -> false
        // Night mode is active, we're using dark theme
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> null
    }
}