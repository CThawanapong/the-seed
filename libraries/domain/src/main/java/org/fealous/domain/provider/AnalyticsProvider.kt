package org.fealous.domain.provider

import org.fealous.data.base.model.AnalyticEvent
import org.fealous.data.base.model.interfaces.AnalyticProviderType

class AnalyticsProvider(
    private val analyticProviderList: List<AnalyticProviderType>
) {
    fun log(analyticEvent: AnalyticEvent) {
        analyticProviderList.forEach {
            it.log(analyticEvent.name(it), analyticEvent.parameters(it))
        }
    }
}