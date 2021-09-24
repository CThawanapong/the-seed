package org.fealous.theseed.base.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class ScrollingViewWithBottomNavigationBehavior(context: Context, attrs: AttributeSet) :
    AppBarLayout.ScrollingViewBehavior(context, attrs) {
    var navigationBarHeight = 0

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val result = super.layoutDependsOn(
            parent,
            child,
            dependency
        ) || dependency is BottomNavigationView

        return updateViewBound(dependency, child, result)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val result = super.onDependentViewChanged(parent, child, dependency)
        return updateViewBound(dependency, child, result)
    }

    private fun updateViewBound(
        dependency: View,
        child: View,
        result: Boolean
    ): Boolean {
        return if (dependency is AppBarLayout) {
            val bottomPadding = calculateBottomPadding(dependency)
            val paddingChanged = updateBottomMargin(child, bottomPadding)
            paddingChanged || result
        } else if (dependency is BottomNavigationView && dependency.isVisible) {
            val paddingChanged = updateBottomPadding(dependency.height, child)
            paddingChanged || result
        } else if (dependency is BottomNavigationView && !dependency.isVisible) {
            val paddingChanged = updateBottomPadding(navigationBarHeight, child)
            paddingChanged || result
        } else {
            result
        }
    }

    private fun updateBottomMargin(
        child: View,
        bottomMargin: Int
    ): Boolean {
        val marginLayoutParams = child.layoutParams as ViewGroup.MarginLayoutParams
        return when {
            bottomMargin != marginLayoutParams.bottomMargin -> {
                marginLayoutParams.bottomMargin = bottomMargin
                child.requestLayout()
                true
            }
            else -> false
        }
    }

    private fun updateBottomPadding(bottomPadding: Int, child: View): Boolean {
        val paddingChanged = bottomPadding != child.paddingBottom
        if (paddingChanged) {
            child.updatePadding(
                bottom = bottomPadding
            )
            child.requestLayout()
        }
        return paddingChanged
    }

    // Calculate the padding needed to keep the bottom of the view pager's content at the same location on the screen.
    private fun calculateBottomPadding(dependency: AppBarLayout): Int {
        return when (this.overlayTop) {
            0 -> {
                val totalScrollRange = dependency.totalScrollRange
                totalScrollRange + dependency.top
            }
            else -> 0
        }
    }
}
