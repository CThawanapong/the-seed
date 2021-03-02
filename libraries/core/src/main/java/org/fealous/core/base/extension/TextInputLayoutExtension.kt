package org.fealous.core.base.extension

import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy

/**
 * Created by CThawanapong on 13/11/2017 AD.
 * Email: c.thawanapong@gmail.com
 */

fun TextInputLayout.showError(@StringRes errorMessageRes: Int) {
    this.isErrorEnabled = true
    this.error = this.context.getString(errorMessageRes)
}

fun TextInputLayout.hideError() {
    this.isErrorEnabled = false
}

fun TextInputLayout.enableClearErrorOnTextChanged(subscription: CompositeDisposable) {
    this.editText?.let { it ->
        subscription += it.afterTextChangeEvents()
            .subscribeBy(
                onNext = { hideError() }
            )
    }
}