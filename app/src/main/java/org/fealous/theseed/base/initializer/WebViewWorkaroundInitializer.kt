package org.fealous.theseed.base.initializer

import android.content.Context
import android.webkit.WebView
import androidx.startup.Initializer

class WebViewWorkaroundInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        WebView(context).destroy()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }
}