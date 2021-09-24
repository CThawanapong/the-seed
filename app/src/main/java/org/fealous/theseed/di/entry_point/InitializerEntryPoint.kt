package org.fealous.theseed.di.entry_point

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import org.fealous.theseed.base.initializer.EpoxyControllerInitializer
import org.fealous.theseed.base.initializer.TimberInitializer

@EntryPoint
@InstallIn(SingletonComponent::class)
interface InitializerEntryPoint {
    companion object {
        // a helper method to resolve the InitializerEntryPoint from the context
        fun resolve(context: Context): InitializerEntryPoint {
            val appContext = context.applicationContext ?: throw IllegalStateException()
            return EntryPointAccessors.fromApplication(
                appContext,
                InitializerEntryPoint::class.java
            )
        }
    }

    fun inject(initializer: TimberInitializer)
    fun inject(initializer: EpoxyControllerInitializer)
}
