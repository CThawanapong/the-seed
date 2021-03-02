package org.fealous.domain.provider.analytic

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import org.fealous.data.base.model.AnalyticEvent
import org.fealous.data.base.model.interfaces.AnalyticProviderType
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticProviderType {
    override fun log(eventName: String, parameter: Map<String, Any>) {
        when {
            eventName.isNotEmpty() -> {
                val bundle = Bundle()
                parameter.forEach { (key, value) ->
                    when (value) {
                        is Int -> bundle.putInt(key, value)
                        is Float -> bundle.putFloat(key, value)
                        is Double -> bundle.putDouble(key, value)
                        is String -> bundle.putString(key, value)
                        is ArrayList<*> -> {
                            try {
                                (value as? ArrayList<out Bundle>)?.let {
                                    when {
                                        it.size == 1 -> bundle.putBundle(key, it.first())
                                        else -> bundle.putParcelableArrayList(key, it)
                                    }
                                }
                            } catch (ignored: Exception) {
                            }
                        }
                    }
                }

                firebaseAnalytics.logEvent(eventName, bundle)
            }
        }
    }

    override fun name(analyticEvent: AnalyticEvent): String {
        return when (analyticEvent) {
            is AnalyticEvent.ScreenView -> FirebaseAnalytics.Event.SCREEN_VIEW
        }
    }

    override fun parameter(analyticEvent: AnalyticEvent): Map<String, Any> {
        return when (analyticEvent) {
            is AnalyticEvent.ScreenView -> mapOf(
                FirebaseAnalytics.Param.SCREEN_CLASS to analyticEvent.screenClass,
                FirebaseAnalytics.Param.SCREEN_NAME to analyticEvent.screenName
            )
        }
    }
}