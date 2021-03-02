package org.fealous.core.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.fealous.core.R
import org.fealous.core.base.interfaces.ConsumableEvent
import org.fealous.data.base.model.AnalyticEvent
import org.fealous.domain.SchedulersFacade
import org.fealous.domain.provider.AnalyticsProvider
import timber.log.Timber
import javax.inject.Inject

abstract class BaseDialogFragment(@LayoutRes contentLayoutId: Int) : DialogFragment(contentLayoutId), ConsumableEvent {
    companion object {
        private val TAG = BaseDialogFragment::class.java.simpleName
    }

    val context: Context
        @JvmName("getNonNullContext")
        get() = requireContext()

    val activity: FragmentActivity
        @JvmName("getNonNullActivity")
        get() = requireActivity()

    val fragmentManager: FragmentManager
        @JvmName("getNonNullFragmentManager")
        get() = parentFragmentManager

    // Injection
    @Inject
    lateinit var analyticsProvider: AnalyticsProvider

    @Inject
    lateinit var schedulersFacade: SchedulersFacade

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(context) }
    protected var subscriptions = CompositeDisposable()
    protected abstract val screenName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val screenName = screenName
            .replace("Activity", "")
            .replace("DialogFragment", "")
            .replace("Fragment", "")
        analyticsProvider.log(AnalyticEvent.ScreenView(this.javaClass.simpleName, screenName))
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    protected fun showProgressDialog() {
        try {
            progressDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            Timber.e(e)
        }

        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.view_progress_dialog)
    }

    protected fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    protected fun showAlertDialog(
        @StringRes titleRes: Int,
        message: String,
        shouldFinish: Boolean
    ) {
        AlertDialog.Builder(context)
            .setTitle(getString(titleRes))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                dialogInterface.dismiss()
                if (shouldFinish) activity.finish()
            }
            .show()
    }
}