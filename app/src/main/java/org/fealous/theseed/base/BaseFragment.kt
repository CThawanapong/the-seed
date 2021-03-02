package org.fealous.theseed.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.GlidePreloadRequestHolder
import com.airbnb.epoxy.preload.EpoxyModelPreloader
import com.airbnb.epoxy.preload.ViewMetadata
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.fealous.core.base.BaseAndroidViewModel
import org.fealous.core.base.BaseViewModel
import org.fealous.core.base.epoxy.EpoxyViewBindingModelWithHolder
import org.fealous.core.base.interfaces.AppBarConfig
import org.fealous.core.base.interfaces.ConsumableEvent
import org.fealous.core.base.observe
import org.fealous.data.base.model.AnalyticEvent
import org.fealous.domain.SchedulersFacade
import org.fealous.domain.provider.AnalyticsProvider
import org.fealous.domain.provider.PreferenceProvider
import org.fealous.theseed.R
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId),
    ConsumableEvent,
    AppBarConfig {
    companion object {
        @JvmStatic
        private val TAG = BaseFragment::class.java.simpleName
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
    lateinit var schedulersFacade: SchedulersFacade

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var epoxyModelPreloader: EpoxyModelPreloader<EpoxyViewBindingModelWithHolder<*>, ViewMetadata?, GlidePreloadRequestHolder>

    @Inject
    lateinit var analyticsProvider: AnalyticsProvider

    // Data Members
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(context) }
    protected var subscriptions = CompositeDisposable()
    protected abstract val screenName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        subscriptions = CompositeDisposable()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)
        viewModelList.forEach {
            when (it) {
                is BaseViewModel -> {
                    it.loadingLiveEvent
                        .observe(viewLifecycleOwner) { isLoading ->
                            if (isLoading) {
                                showProgressDialog()
                            } else {
                                hideProgressDialog()
                            }
                        }

                    it.errorLiveEvent
                        .observe(viewLifecycleOwner) { messageRes ->
                            // TODO Handle Error
                        }

                    it.errorMessageLiveEvent
                        .observe(viewLifecycleOwner) { message ->
                            // TODO Handle Error
                        }
                }
                is BaseAndroidViewModel -> {
                    it.loadingLiveEvent
                        .observe(viewLifecycleOwner) { isLoading ->
                            if (isLoading) {
                                showProgressDialog()
                            } else {
                                hideProgressDialog()
                            }
                        }

                    it.errorLiveEvent
                        .observe(viewLifecycleOwner) { messageRes ->
                            // TODO Handle Error
                        }

                    it.errorMessageLiveEvent
                        .observe(viewLifecycleOwner) { message ->
                            // TODO Handle Error
                        }
                }
            }
        }
    }

    private fun initInstance(view: View, savedInstanceState: Bundle?) {
        val coordinatorLayout = view.findViewById<CoordinatorLayout>(R.id.coordinatorLayout)
        coordinatorLayout?.let { layout ->
            ViewCompat.setOnApplyWindowInsetsListener(layout) { _, windowInsets ->
                val bottomNavigationView =
                    activity.findViewById<BottomNavigationView>(R.id.bottomNavigation)
                val contentView: ViewGroup? =
                    view.findViewById(R.id.recyclerView)
                        ?: view.findViewById(R.id.nestedScrollView)

                setupToolbar(layout, bottomNavigationView, contentView, windowInsets)
                windowInsets
            }
        }

        view.findViewById<MaterialToolbar>(R.id.toolbar)?.let { toolbar ->
            val collapsingToolbarLayout =
                view.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbarLayout)
            when {
                collapsingToolbarLayout != null -> {
                    collapsingToolbarLayout.setupWithNavController(
                        toolbar, findNavController(),
                        AppBarConfiguration(
                            setOf()
                        )
                    )
                    collapsingToolbarLayout.title = toolbarTitle
                }
                else -> toolbar.setupWithNavController(
                    findNavController(),
                    AppBarConfiguration(
                        setOf()
                    )
                )
            }
        }
    }

    override fun setupToolbar(
        rootView: ViewGroup,
        bottomNavigationView: BottomNavigationView?,
        contentView: ViewGroup?,
        windowInsetsCompat: WindowInsetsCompat
    ) {
        super.setupToolbar(rootView, bottomNavigationView, contentView, windowInsetsCompat)

        val bottomInset =
            windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
        contentView?.updatePadding(
            bottom = when (bottomNavigationView?.isVisible) {
                true -> bottomInset + context.resources.getDimensionPixelSize(
                    R.dimen.default_bottom_navigation_height
                )
                else -> bottomInset
            }
        )

        view?.findViewById<RecyclerView>(R.id.recyclerViewAppbar)
            ?.let { recyclerViewAppbar ->
                recyclerViewAppbar.updatePadding(
                    top = when {
                        shouldAddToolbarPadding -> calculateDefaultActionBarHeight() - context.resources.getDimensionPixelSize(
                            R.dimen.default_app_bar_offset
                        )
                        else -> recyclerViewAppbar.paddingTop
                    }
                )

                val contentLayoutParams =
                    contentView?.layoutParams as? CoordinatorLayout.LayoutParams
                (contentLayoutParams?.behavior as? AppBarLayout.ScrollingViewBehavior)?.overlayTop =
                    overlapHeight
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val screenName = screenName
            .replace("Activity", "")
            .replace("DialogFragment", "")
            .replace("Fragment", "")
        analyticsProvider.log(AnalyticEvent.ScreenView(this.javaClass.simpleName, screenName))
    }

    override fun onDestroyView() {
        subscriptions.clear()
        super.onDestroyView()
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

    protected fun hideSoftKeyboard() {
        activity.currentFocus?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    protected fun showSoftKeyboard() {
        activity.currentFocus?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    protected fun calculateDefaultActionBarHeight(): Int {
        val tv = TypedValue()
        return when {
            context.theme.resolveAttribute(
                R.attr.actionBarSize,
                tv,
                true
            ) -> TypedValue.complexToDimensionPixelSize(
                tv.data,
                resources.displayMetrics
            )
            else -> 0
        }
    }

    override val actionBarHeight: Int
        get() = resources.getDimensionPixelSize(R.dimen.collapsing_appbar_height)

    override val toolbarTitle: String
        get() = findNavController().currentDestination?.label?.toString() ?: ""
}