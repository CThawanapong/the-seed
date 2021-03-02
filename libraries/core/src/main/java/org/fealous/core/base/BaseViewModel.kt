package org.fealous.core.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.zhuinden.eventemitter.EventEmitter
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposables by lazy { CompositeDisposable() }
    val loadingLiveEvent by lazy { EventEmitter<Boolean>() }
    val errorLiveEvent by lazy { EventEmitter<Int>() }
    val errorMessageLiveEvent by lazy { EventEmitter<String>() }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    protected fun showLoading() {
        loadingLiveEvent.emit(true)
    }

    protected fun hideLoading() {
        loadingLiveEvent.emit(false)
    }

    protected fun showError(@StringRes error: Int) {
        errorLiveEvent.emit(error)
    }

    protected fun showError(errorMessage: String) {
        errorMessageLiveEvent.emit(errorMessage)
    }
}