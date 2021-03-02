package org.fealous.theseed.base.model

import android.os.Parcelable
import com.google.android.material.snackbar.Snackbar
import kotlinx.parcelize.Parcelize

@Parcelize
data class SnackbarConfig(
    val message: String,
    val length: Int = Snackbar.LENGTH_SHORT
) : Parcelable