package org.fealous.theseed

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.fealous.core.base.observe
import org.fealous.core.base.provider.SystemUiVisibilityProvider
import org.fealous.theseed.base.BaseActivity
import org.fealous.theseed.base.behavior.ScrollingViewWithBottomNavigationBehavior
import org.fealous.theseed.databinding.ActivityMainBinding
import org.fealous.theseed.launcher.LauncherViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    companion object {
        @JvmStatic
        private val TAG = MainActivity::class.java.simpleName
    }

    // Injection
    @Inject
    lateinit var mSystemUiVisibilityProvider: SystemUiVisibilityProvider

    // Data Members
    private lateinit var mBinding: ActivityMainBinding
    private val mLauncherViewModel by viewModels<LauncherViewModel>()
    private val mMainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            window.setBackgroundDrawableResource(R.drawable.bg_splash_screen)
            hideUIBar()
        } else {
            mBinding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(mBinding.root)
            showUIBar()
            initView()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        //region ViewModel
        mLauncherViewModel.launchCompleteLiveEvent
            .observe(this) {
                it?.let { either ->
                    if (savedInstanceState == null) {
                        mBinding = ActivityMainBinding.inflate(layoutInflater)
                        setContentView(mBinding.root)
                        window.setBackgroundDrawable(
                            ColorDrawable(
                                ContextCompat.getColor(
                                    this,
                                    R.color.colorBackground
                                )
                            )
                        )
                        showUIBar()
                        initView()
                    }
                }
            }

        mMainViewModel.showSnackbarLiveEvent
            .observe(this) {
                showSnackBar(it.message, it.length)
            }
        //endregion

        mLauncherViewModel.loadInit()
    }

    private fun initView() {
        with(mBinding) {
            ViewCompat.setOnApplyWindowInsetsListener(coordinatorLayout) { view, windowInsets ->
                val bottomInset =
                    windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                (((supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment)?.view?.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior as? ScrollingViewWithBottomNavigationBehavior)?.navigationBarHeight =
                    bottomInset
                bottomNavigation.updatePadding(bottom = bottomInset)
                snackBarPosition.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = bottomInset
                }

                windowInsets
            }

            findNavController(R.id.navHostFragment).let {
                bottomNavigation.apply {
                    setupWithNavController(it)
                }
                it.addOnDestinationChangedListener { controller, destination, arguments ->
                    hideSoftKeyboard()
                    when (destination.id) {
                        else -> {
                            bottomNavigation.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun hideUIBar() {
        // for new api versions.
        val decorView = window.decorView
        val uiOptions = mSystemUiVisibilityProvider.fullScreenImmersiveUiVisiblity
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorOnPrimary)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        decorView.systemUiVisibility = uiOptions
    }

    private fun showUIBar() {
        window.decorView.systemUiVisibility = mSystemUiVisibilityProvider.visibleSystemUiVisibility

        val winParams = window.attributes
        var flags = winParams.flags
        flags = flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            winParams.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
        }
        winParams.flags = flags
        window.attributes = winParams
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility =
            (window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    private fun showSnackBar(message: String, length: Int) {
        with(mBinding) {
            Snackbar.make(coordinatorLayout, message, length).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navHostFragment).navigateUp() || super.onSupportNavigateUp()
    }

    override val viewModelList: List<ViewModel>
        get() = super.viewModelList.toMutableList().apply {
            add(mLauncherViewModel)
            add(mMainViewModel)
        }
}