package org.fealous.theseed.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.fealous.core.base.BaseAndroidViewModel
import org.fealous.core.base.BaseViewModel
import org.fealous.core.base.interfaces.ConsumableEvent
import org.fealous.core.base.observe
import org.fealous.domain.SchedulersFacade
import org.fealous.domain.base.extension.wrap
import org.fealous.domain.provider.PreferenceProvider
import org.fealous.theseed.R
import org.fealous.theseed.TheSeedApplication
import timber.log.Timber
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), ConsumableEvent {
    companion object {
        @JvmStatic
        private val TAG = BaseActivity::class.java.simpleName
    }

    // Injection
    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var schedulersFacade: SchedulersFacade

    // Data Members
    protected var subscriptions = CompositeDisposable()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            (it.applicationContext as? TheSeedApplication)?.let { application ->
                super.attachBaseContext(
                    newBase.wrap(
                        Locale(application.preferenceProvider.languageCode)
                    )
                )
            } ?: super.attachBaseContext(newBase)
        } ?: super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptions = CompositeDisposable()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewModelList.forEach {
            when (it) {
                is BaseViewModel -> {
                    it.loadingLiveEvent
                        .observe(this) {
                            if (it) {
                                showProgressDialog()
                            } else {
                                hideProgressDialog()
                            }
                        }

                    it.errorLiveEvent
                        .observe(this) {
                            // TODO Handle error
                        }
                }
                is BaseAndroidViewModel -> {
                    it.loadingLiveEvent
                        .observe(this) {
                            if (it) {
                                showProgressDialog()
                            } else {
                                hideProgressDialog()
                            }
                        }

                    it.errorLiveEvent
                        .observe(this) {
                            // TODO Handle error
                        }
                }
            }
        }
    }

    override fun onDestroy() {
        subscriptions.clear()
        super.onDestroy()
    }

    private fun showProgressDialog() {
        try {
            progressDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            Timber.e(e)
        }

        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.view_progress_dialog)
    }

    private fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    protected fun hideSoftKeyboard() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}