package org.fealous.core.base.epoxy

import android.content.Context
import android.view.View
import android.view.ViewParent
import androidx.viewbinding.ViewBinding
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.preload.Preloadable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.fealous.core.base.interfaces.PreloaderImage
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap

abstract class EpoxyViewBindingModelWithHolder<in T : ViewBinding> :
    EpoxyModelWithHolder<EpoxyViewBindingHolder>(), PreloaderImage {
    protected val subscriptions: CompositeDisposable by lazy { CompositeDisposable() }

    @Suppress("UNCHECKED_CAST")
    override fun bind(holder: EpoxyViewBindingHolder) {
        (holder.viewBinding as T).bind(holder.viewBinding.root.context)
    }

    @Suppress("UNCHECKED_CAST")
    override fun unbind(holder: EpoxyViewBindingHolder) {
        (holder.viewBinding as T).unbind(holder.viewBinding.root.context)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewAttachedToWindow(holder: EpoxyViewBindingHolder) {
        (holder.viewBinding as T).onViewAttachedToWindow(holder.viewBinding.root.context)
    }

    abstract fun T.bind(context: Context)

    open fun T.unbind(context: Context) {

    }

    open fun T.onViewAttachedToWindow(context: Context) {

    }

    open fun preloaderViews(binding: T): List<View> = emptyList()

    @Suppress("UNCHECKED_CAST")
    override fun createNewHolder(parent: ViewParent): EpoxyViewBindingHolder {
        return EpoxyViewBindingHolder(this::class.java) { viewBinding ->
            preloaderViews((viewBinding as T))
        }
    }

    override fun onViewDetachedFromWindow(holder: EpoxyViewBindingHolder) {
        subscriptions.clear()
        super.onViewDetachedFromWindow(holder)
    }

    override fun preloaderImages(): List<String> {
        return emptyList()
    }
}

// Static cache of a method pointer for each type of item used.
private val sBindingMethodByClass = ConcurrentHashMap<Class<*>, Method>()

@Suppress("UNCHECKED_CAST")
@Synchronized
private fun getBindMethodFrom(javaClass: Class<*>): Method =
    sBindingMethodByClass.getOrPut(javaClass) {
        val actualTypeOfThis = getSuperclassParameterizedType(javaClass)
        val viewBindingClass = actualTypeOfThis.actualTypeArguments[0] as Class<ViewBinding>
        viewBindingClass.getDeclaredMethod("bind", View::class.java)
            ?: error("The binder class ${javaClass.canonicalName} should have a method bind(View)")
    }

private fun getSuperclassParameterizedType(klass: Class<*>): ParameterizedType {
    val genericSuperclass = klass.genericSuperclass
    return (genericSuperclass as? ParameterizedType)
        ?: getSuperclassParameterizedType(genericSuperclass as Class<*>)
}

class EpoxyViewBindingHolder(
    private val epoxyModelClass: Class<*>,
    private val preLoader: (ViewBinding) -> List<View> = { emptyList() }
) : EpoxyHolder(), Preloadable {
    // Using reflection to get the static binding method.
    // Lazy so it's computed only once by instance, when the 1st ViewHolder is actually created.
    private val bindingMethod by lazy { getBindMethodFrom(epoxyModelClass) }

    internal lateinit var viewBinding: ViewBinding
    override fun bindView(itemView: View) {
        // The 1st param is null because the binding method is static.
        viewBinding = bindingMethod.invoke(null, itemView) as ViewBinding
    }

    override val viewsToPreload: List<View>
        get() {
            return preLoader(viewBinding)
        }
}