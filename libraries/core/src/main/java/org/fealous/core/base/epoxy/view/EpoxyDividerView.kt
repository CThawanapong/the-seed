package org.fealous.core.base.epoxy.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import org.fealous.core.databinding.ListItemDividerBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = true)
class EpoxyDividerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        private val TAG = EpoxyDividerView::class.java.simpleName
    }

    private lateinit var mBinding: ListItemDividerBinding

    @DimenRes
    var dimenRes: Int? = null
        @CallbackProp set

    @DimenRes
    var startMarginRes: Int? = null
        @CallbackProp set

    @DimenRes
    var endMarginRes: Int? = null
        @CallbackProp set

    @ColorRes
    var color: Int? = null
        @CallbackProp set

    init {
        initInflate()
        initInstance(context, attrs)
    }

    private fun initInflate() {
        mBinding = ListItemDividerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun initInstance(context: Context, attrs: AttributeSet?) {
        attrs?.let {
        }
    }

    @AfterPropsSet
    fun useProps() {
        with(mBinding) {
            dimenRes?.let { res ->
                divider.layoutParams.height = context.resources.getDimensionPixelSize(res)
                divider.requestLayout()
            }

            divider.updateLayoutParams<MarginLayoutParams> {
                this.marginStart =
                    startMarginRes?.let { res -> context.resources.getDimensionPixelSize(res) } ?: 0
                this.marginEnd =
                    endMarginRes?.let { res -> context.resources.getDimensionPixelSize(res) } ?: 0
            }

            color?.let { dividerColor ->
                divider.setBackgroundResource(dividerColor)
            }
        }
    }
}