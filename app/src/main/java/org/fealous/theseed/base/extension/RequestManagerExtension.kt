package org.fealous.theseed.base.extension

import android.graphics.Bitmap
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

fun RequestManager.loadImage(url: String): RequestBuilder<Bitmap> {
    val options = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)
        .dontAnimate()
        .signature(ObjectKey(url + "_preloading"))

    return asBitmap()
        .apply(options)
        .load(url)
}
