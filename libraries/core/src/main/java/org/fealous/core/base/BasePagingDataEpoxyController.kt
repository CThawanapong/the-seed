package org.fealous.core.base

import android.content.Context
import android.os.Handler
import androidx.paging.LoadState
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import dagger.hilt.android.qualifiers.ActivityContext
import org.fealous.core.base.epoxy.view.epoxyEmptyView
import org.fealous.core.base.epoxy.view.epoxyLoadingItemView
import org.fealous.core.base.epoxy.view.epoxyLoadingView
import org.fealous.data.base.di.ASYNC_BACKGROUND_THREAD_HANDLER
import javax.inject.Inject
import javax.inject.Named

abstract class BasePagingDataEpoxyController<T : Any>(
    @ActivityContext private val context: Context,
    @Named(ASYNC_BACKGROUND_THREAD_HANDLER) handler: Handler
) : PagingDataEpoxyController<T>(
    modelBuildingHandler = handler,
    diffingHandler = handler
) {
    companion object {
        @JvmStatic
        private val TAG = BasePagingDataEpoxyController::class.java.simpleName
    }

    init {
        addLoadStateListener {
            isLoading = it.refresh is LoadState.Loading
            isError = it.refresh is LoadState.Error
            isLoadingItem = it.append is LoadState.Loading
        }
    }

    var isLoading: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                requestModelBuild()
            }
        }
    var isError: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                requestModelBuild()
            }
        }
    var isLoadingItem: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                requestModelBuild()
            }
        }

    override fun addModels(models: List<EpoxyModel<*>>) {
        when {
            models.isEmpty() && isLoading -> epoxyLoadingView {
                id("placeholder")
                spanSizeOverride { totalSpanCount, _, _ ->
                    totalSpanCount
                }
            }
            models.isEmpty() -> epoxyEmptyView {
                id("empty")
                spanSizeOverride { totalSpanCount, _, _ ->
                    totalSpanCount
                }
            }
            else -> {
                super.addModels(models)

                if (isLoadingItem) {
                    epoxyLoadingItemView {
                        id("loadingItem")
                        spanSizeOverride { totalSpanCount, _, _ ->
                            totalSpanCount
                        }
                    }
                }
            }
        }
    }
}