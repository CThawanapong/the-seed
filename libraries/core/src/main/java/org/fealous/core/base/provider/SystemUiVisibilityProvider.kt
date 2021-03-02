package org.fealous.core.base.provider

import android.view.View
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemUiVisibilityProvider @Inject constructor() {
    val visibleSystemUiVisibility: Int
        get() = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

    val fullScreenImmersiveUiVisiblity: Int
        get() = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
}