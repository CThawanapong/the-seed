package org.fealous.core.base.extension

import android.graphics.Paint
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat

/**
 * Created by CThawanapong on 6/16/2017 AD.
 * Email: c.thawanapong@gmail.com
 */
fun AppCompatTextView.futureText(text: String) {
    this.setTextFuture(
        PrecomputedTextCompat.getTextFuture(
            text,
            TextViewCompat.getTextMetricsParams(this), null
        )
    )
}

fun TextView.enableStrikeThrough() {
    this.paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.disableStrikeThrough() {
    this.paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}