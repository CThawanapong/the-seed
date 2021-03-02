package org.fealous.core.base.epoxy.view

 import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import org.fealous.core.R
import org.fealous.core.databinding.ListItemEmptyBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT, fullSpan = true)
class EpoxyEmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        private val TAG = EpoxyEmptyView::class.java.simpleName
    }

    private lateinit var mBinding: ListItemEmptyBinding

    @DrawableRes
    var emptyRes: Int? = null
        @CallbackProp set

    @StringRes
    var titleRes: Int? = null
        @CallbackProp set

    init {
        initInflate()
        initInstance(context, attrs)
    }

    private fun initInflate() {
        mBinding = ListItemEmptyBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun initInstance(context: Context, attrs: AttributeSet?) {
        attrs?.let {
        }
    }

    @AfterPropsSet
    fun useProps() {
        with(mBinding) {
            imageViewEmpty.apply {
                setImageResource(emptyRes?.let { it } ?: R.drawable.ic_empty_box)
                if (emptyRes == null) {
                    setColorFilter(
                        ContextCompat.getColor(context, R.color.colorOnSurface),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            }
            textView.isVisible = titleRes != null
            titleRes?.let {
                textView.setText(it)
            }
        }
    }
}