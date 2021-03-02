package org.fealous.data.base.model.interfaces

import org.fealous.data.base.model.AnalyticEvent

interface AnalyticProviderType {
    fun log(eventName: String, parameter: Map<String, Any> = emptyMap())
    fun name(analyticEvent: AnalyticEvent): String
    fun parameter(analyticEvent: AnalyticEvent): Map<String, Any>
}