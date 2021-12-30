package org.fealous.theseed

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.google.android.material.textview.MaterialTextView
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.fealous.core.base.delegate.viewBinding
import org.fealous.theseed.databinding.ActivityCrashBinding
import timber.log.Timber

@AndroidEntryPoint
class CrashActivity : AppCompatActivity(R.layout.activity_crash) {
    companion object {
        @JvmStatic
        private val TAG = CrashActivity::class.java.simpleName
    }

    private val mBinding by viewBinding(ActivityCrashBinding::inflate)
    private val mConfig by lazy { CustomActivityOnCrash.getConfigFromIntent(intent) }
    private var subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptions = CompositeDisposable()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        with(mBinding) {
            mConfig?.let { config ->
                buttonRestart.setText(
                    when {
                        config.isShowRestartButton && config.restartActivityClass != null -> R.string.crash_restart
                        else -> R.string.crash_close
                    }
                )

                buttonStacktrace.isVisible = config.isShowErrorDetails

                subscriptions += buttonRestart.clicks()
                    .subscribeBy(
                        onNext = {
                            when {
                                config.isShowRestartButton && config.restartActivityClass != null -> CustomActivityOnCrash.restartApplication(
                                    this@CrashActivity,
                                    config
                                )
                                else -> CustomActivityOnCrash.closeApplication(
                                    this@CrashActivity,
                                    config
                                )
                            }
                        }
                    )

                subscriptions += buttonStacktrace.clicks()
                    .subscribeBy(
                        onError = Timber::e,
                        onNext = {
                            val dialog = AlertDialog.Builder(this@CrashActivity)
                                .setTitle(R.string.customactivityoncrash_error_activity_error_details_title)
                                .setMessage(
                                    CustomActivityOnCrash.getAllErrorDetailsFromIntent(
                                        this@CrashActivity,
                                        intent
                                    )
                                )
                                .setPositiveButton(
                                    R.string.customactivityoncrash_error_activity_error_details_close,
                                    null
                                )
                                .setNeutralButton(
                                    R.string.customactivityoncrash_error_activity_error_details_copy
                                ) { _, _ -> copyErrorToClipboard() }
                                .show()

                            dialog.findViewById<MaterialTextView>(android.R.id.message)
                                ?.setTextSize(
                                    TypedValue.COMPLEX_UNIT_PX,
                                    resources.getDimension(R.dimen.customactivityoncrash_error_activity_error_details_text_size)
                                )
                        }
                    )
            } ?: finish()
        }
    }

    private fun copyErrorToClipboard() {
        val errorInformation =
            CustomActivityOnCrash.getAllErrorDetailsFromIntent(this@CrashActivity, intent)
        val clipboard = getSystemService<ClipboardManager>()

        if (clipboard != null) {
            val clip = ClipData.newPlainText(
                getString(R.string.customactivityoncrash_error_activity_error_details_clipboard_label),
                errorInformation
            )
            clipboard.setPrimaryClip(clip)
        }
    }

    override fun onDestroy() {
        subscriptions.clear()
        super.onDestroy()
    }
}
