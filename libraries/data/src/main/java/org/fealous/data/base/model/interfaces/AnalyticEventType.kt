package org.fealous.data.base.model.interfaces

interface AnalyticEventType {
    fun name(analyticProviderType: AnalyticProviderType): String
    fun parameters(analyticProviderType: AnalyticProviderType): Map<String, Any>
}