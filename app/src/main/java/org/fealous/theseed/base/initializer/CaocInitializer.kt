package org.fealous.theseed.base.initializer

import android.content.Context
import androidx.startup.Initializer
import cat.ereza.customactivityoncrash.config.CaocConfig
import org.fealous.theseed.BuildConfig
import org.fealous.theseed.CrashActivity
import org.fealous.theseed.R

class CaocInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            .enabled(true)
            .showErrorDetails(
                when (BuildConfig.BUILD_TYPE) {
                    "debug", "stage" -> true
                    else -> false
                }
            )
            .showRestartButton(true)
            .errorDrawable(R.mipmap.ic_launcher_round)
            .errorActivity(CrashActivity::class.java)
            .apply()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }
}
