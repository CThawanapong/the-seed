package org.fealous.theseed.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.serjltt.moshi.adapters.DeserializeOnly
import com.serjltt.moshi.adapters.SerializeOnly
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.fealous.core.base.moshi.NullSafetyJsonAdapter
import org.fealous.domain.SchedulersFacade
import org.fealous.theseed.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        @JvmStatic
        private val TAG = NetworkModule::class.java.simpleName

        const val ENDPOINT = "ENDPOINT"
        const val HTTP_CLIENT = "HTTP_CLIENT"
        const val RETROFIT = "RETROFIT"
    }

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(NullSafetyJsonAdapter())
        .add(DeserializeOnly.ADAPTER_FACTORY)
        .add(SerializeOnly.ADAPTER_FACTORY)
        .build()

    @Singleton
    @Provides
    fun provideInterceptor() = HttpLoggingInterceptor(
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag(TAG).d(message)
            }
        }
    ).apply {
        setLevel(
            when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        )
    }

    @Singleton
    @Provides
    fun provideChuckerCollector(@ApplicationContext context: Context) = ChuckerCollector(
        context = context
    )

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context) =
        ChuckerInterceptor.Builder(context)
            .build()

    @Singleton
    @Provides
    @Named(HTTP_CLIENT)
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val chainBuilder = chain.request().newBuilder()
                // Add required headers
                val request = chainBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    @Named(RETROFIT)
    fun provideRetrofit(
        @Named(ENDPOINT) endpoint: String,
        moshi: Moshi,
        @Named(HTTP_CLIENT) okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(endpoint)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideSchedulerFacade() = SchedulersFacade()
}
