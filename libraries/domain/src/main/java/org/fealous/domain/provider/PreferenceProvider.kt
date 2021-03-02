package org.fealous.domain.provider

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import org.fealous.domain.base.extension.wrap
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val locale: Locale
) {
    companion object {
        @JvmStatic
        private val TAG = PreferenceProvider::class.java.simpleName

        const val KEY_LANGUAGE_CODE = "language_code"
    }

    val languageCode: String
        get() = sharedPreferences.getString(KEY_LANGUAGE_CODE, locale.language) ?: locale.language

    val currentLocale: Locale
        get() = Locale(languageCode)

    val wrapContext: Context
        get() = context.wrap(currentLocale)

    fun updateLanguageCode(languageCode: String) {
        sharedPreferences.edit()
            .putString(KEY_LANGUAGE_CODE, languageCode)
            .apply()
    }
}