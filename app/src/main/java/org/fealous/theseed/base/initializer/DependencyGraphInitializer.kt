package org.fealous.theseed.base.initializer

import android.content.Context
import androidx.startup.Initializer
import org.fealous.theseed.di.entry_point.InitializerEntryPoint

class DependencyGraphInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}