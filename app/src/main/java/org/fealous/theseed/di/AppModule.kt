package org.fealous.theseed.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.WindowManager
import androidx.annotation.XmlRes
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.airbnb.epoxy.GlidePreloadRequestHolder
import com.airbnb.epoxy.glidePreloader
import com.airbnb.epoxy.preload.EpoxyModelPreloader
import com.airbnb.epoxy.preload.ViewMetadata
import com.chuckerteam.chucker.api.ChuckerCollector
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.fealous.core.base.epoxy.EpoxyViewBindingModelWithHolder
import org.fealous.data.base.model.di.APPLICATION_ID
import org.fealous.data.base.model.di.REMOTE_CONFIG_DEFAULTS
import org.fealous.data.base.model.di.VERSION_NAME
import org.fealous.theseed.BuildConfig
import org.fealous.theseed.R
import org.fealous.theseed.base.TheSeedDebugTree
import org.fealous.theseed.base.TheSeedReleaseTree
import org.fealous.theseed.base.extension.loadImage
import org.fealous.theseed.di.NetworkModule.Companion.ENDPOINT
import timber.log.Timber
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    companion object {
        const val SHARE_PREF = "share_pref"
        private const val MINIMUM_FETCH_INTERVAL = 60L
        private const val MINIMUM_FETCH_INTERVAL_PROD = 600L
    }

    @Provides
    @Named(ENDPOINT)
    fun provideEndpoint(): String = BuildConfig.API_ENDPOINT

    @Singleton
    @Provides
    @Named(APPLICATION_ID)
    fun provideApplicationId(): String = BuildConfig.APPLICATION_ID

    @Singleton
    @Provides
    @Named(VERSION_NAME)
    fun provideVersionName(): String = BuildConfig.VERSION_NAME

    @Singleton
    @Provides
    @Named(REMOTE_CONFIG_DEFAULTS)
    @XmlRes
    fun provideRemoteConfigDefault(): Int = R.xml.remote_config_defaults

    @Singleton
    @Provides
    fun provideTree(
        chuckerCollector: ChuckerCollector,
        crashlytics: FirebaseCrashlytics
    ): Timber.Tree {
        return when {
            BuildConfig.DEBUG -> TheSeedDebugTree(chuckerCollector)
            else -> TheSeedReleaseTree(crashlytics)
        }
    }

    @Singleton
    @Provides
    fun provideCrashlytics() = Firebase.crashlytics

    @Singleton
    @Provides
    fun provideRemoteConfigSettings() = FirebaseRemoteConfigSettings.Builder()
        .apply {
            minimumFetchIntervalInSeconds = when {
                BuildConfig.DEBUG -> MINIMUM_FETCH_INTERVAL
                else -> MINIMUM_FETCH_INTERVAL_PROD
            }
        }
        .build()

    @Singleton
    @Provides
    fun provideFirebaseRemoteConfig() = Firebase.remoteConfig

    @Singleton
    @Provides
    fun provideLocale() = Locale.getDefault()

    @Singleton
    @Provides
    fun provideEncryptedSharePreference(@ApplicationContext context: Context): SharedPreferences {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> EncryptedSharedPreferences.create(
                context,
                SHARE_PREF,
                MasterKey.Builder(context)
                    .setKeyGenParameterSpec(MasterKeys.AES256_GCM_SPEC)
                    .build(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            else -> PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

    @Singleton
    @Provides
    fun provideNumberFormat(locale: Locale): NumberFormat = DecimalFormat.getInstance(locale)

    @Singleton
    @Provides
    fun provideWindowManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    @Singleton
    @Provides
    fun provideEpoxyModelPreloader(): EpoxyModelPreloader<EpoxyViewBindingModelWithHolder<*>, ViewMetadata?, GlidePreloadRequestHolder> {
        return glidePreloader { requestManager, epoxyModel, _ ->
            epoxyModel.preloaderImages()
                .map { imageUrl ->
                    requestManager.loadImage(imageUrl)
                }
                .firstOrNull() ?: requestManager.loadImage("")
        }
    }
}