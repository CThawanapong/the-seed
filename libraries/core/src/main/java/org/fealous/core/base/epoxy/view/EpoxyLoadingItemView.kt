package org.fealous.core.base.epoxy.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelView
import org.fealous.core.databinding.ListItemLoadingItemBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = true)
class EpoxyLoadingItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    companion object {
        @JvmStatic
        private val TAG = EpoxyLoadingItemView::class.java.simpleName
    }

    private lateinit var mBinding: ListItemLoadingItemBinding

    init {
        initInflate()
        initInstance(context, attrs)
    }

    private fun initInflate() {
        mBinding = ListItemLoadingItemBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun initInstance(context: Context, attrs: AttributeSet?) {
        attrs?.let {
        }
    }
}