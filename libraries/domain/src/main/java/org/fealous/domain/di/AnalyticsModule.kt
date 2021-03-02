package org.fealous.domain.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.fealous.domain.provider.AnalyticsProvider
import org.fealous.domain.provider.analytic.FirebaseAnalyticsProvider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AnalyticsModule {
    @Singleton
    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context) =
        FirebaseAnalytics.getInstance(context)

    @Singleton
    @Provides
    fun provideAnalyticsProvider(
        firebaseAnalyticsProvider: FirebaseAnalyticsProvider
    ) = AnalyticsProvider(
        listOf(
            firebaseAnalyticsProvider,
        )
    )
}