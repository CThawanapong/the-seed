package org.fealous.data.base.model

import org.fealous.data.base.model.interfaces.AnalyticEventType
import org.fealous.data.base.model.interfaces.AnalyticProviderType

sealed class AnalyticEvent : AnalyticEventType {
    companion object {
        @JvmStatic
        private val TAG = AnalyticEvent::class.java.simpleName
    }

    class ScreenView(
        val screenClass: String,
        val screenName: String
    ) : AnalyticEvent()

    override fun name(analyticProviderType: AnalyticProviderType) = analyticProviderType.name(this)

    override fun parameters(analyticProviderType: AnalyticProviderType) =
        analyticProviderType.parameter(this)
}