package org.fealous.theseed.launcher

import arrow.core.Either
import com.zhuinden.eventemitter.EventEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import org.fealous.core.base.BaseViewModel
import org.fealous.domain.SchedulersFacade
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val schedulersFacade: SchedulersFacade
) : BaseViewModel() {
    companion object {
        @JvmStatic
        private val TAG = LauncherViewModel::class.java.simpleName
    }

    val launchCompleteLiveEvent by lazy { EventEmitter<Either<Throwable, Boolean>>() }

    fun loadInit() {
        launchCompleteLiveEvent.emit(Either.right(true))
    }
}
