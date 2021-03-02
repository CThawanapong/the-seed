package org.fealous.core.base.interfaces

import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

interface AppBarConfig {
    val actionBarHeight: Int
        get() = 0

    val overlapHeight: Int
        get() = 0

    val toolbarTitle: String
        get() = ""

    val appBarElevation: Float
        get() = 0f

    val shouldAddToolbarPadding: Boolean
        get() = false

    fun setupToolbar(
        rootView: ViewGroup,
        bottomNavigationView: BottomNavigationView?,
        contentView: ViewGroup?,
        windowInsetsCompat: WindowInsetsCompat
    ) {
    }
}