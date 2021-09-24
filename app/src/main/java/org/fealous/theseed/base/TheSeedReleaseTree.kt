package org.fealous.theseed.base

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import hu.akarnokd.rxjava3.debug.RxJavaAssemblyException
import timber.log.Timber

class TheSeedReleaseTree(private val crashlytics: FirebaseCrashlytics) : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            Log.ERROR,
            Log.WARN -> {
                val enhancedThrowable =
                    RxJavaAssemblyException.find(t ?: Throwable()) ?: t ?: Throwable()
                crashlytics.recordException(enhancedThrowable)
            }
            else -> {
            }
        }
    }
}
