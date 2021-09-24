package org.fealous.theseed

import android.app.Application
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.eventemitter.EventEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import org.fealous.core.base.BaseAndroidViewModel
import org.fealous.domain.SchedulersFacade
import org.fealous.theseed.base.model.SnackbarConfig
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val schedulersFacade: SchedulersFacade
) : BaseAndroidViewModel(application) {
    companion object {
        @JvmStatic
        private val TAG = MainViewModel::class.java.simpleName
    }

    // Data Members
    val showSnackbarLiveEvent by lazy { EventEmitter<SnackbarConfig>() }
    val triggerDeepLinkLiveEvent by lazy { EventEmitter<String>() }

    fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
        showSnackbarLiveEvent.emit(SnackbarConfig(message, length))
    }

    fun triggerDeepLink(deeplink: String) {
        triggerDeepLinkLiveEvent.emit(deeplink)
    }
}
