package org.fealous.domain.base.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.*

@SuppressLint("NewApi")
fun Context.wrap(newLocale: Locale): ContextWrapper {
    var context = this

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
            val conf = Configuration()
            conf.fontScale = 0f
            conf.setLocale(newLocale)
            context = context.createConfigurationContext(conf)
        }
        else -> {
            val res = context.resources
            val conf = Configuration(res.configuration)
            conf.fontScale = 0f
            conf.locale = newLocale
            res.updateConfiguration(conf, res.displayMetrics)
        }
    }

    return ContextWrapper(context)
}