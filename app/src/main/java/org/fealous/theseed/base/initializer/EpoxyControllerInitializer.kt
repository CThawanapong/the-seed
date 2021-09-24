package org.fealous.theseed.base.initializer

import android.content.Context
import android.os.Handler
import androidx.startup.Initializer
import com.airbnb.epoxy.EpoxyController
import org.fealous.data.base.di.ASYNC_BACKGROUND_THREAD_HANDLER
import org.fealous.theseed.di.entry_point.InitializerEntryPoint
import javax.inject.Inject
import javax.inject.Named

class EpoxyControllerInitializer : Initializer<Unit> {
    @Inject
    @Named(ASYNC_BACKGROUND_THREAD_HANDLER)
    lateinit var handler: Handler

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context)
            .inject(this)

        EpoxyController.defaultDiffingHandler = handler
        EpoxyController.defaultModelBuildingHandler = handler
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }
}
