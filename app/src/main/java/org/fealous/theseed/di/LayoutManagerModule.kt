package org.fealous.theseed.di

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Named

@InstallIn(FragmentComponent::class)
@Module
class LayoutManagerModule {
    companion object {
        const val VERTICAL_ONE_COLUMN_GRID_LAYOUT_MANAGER =
            "VERTICAL_ONE_COLUMN_GRID_LAYOUT_MANAGER"
        const val VERTICAL_TWO_COLUMN_GRID_LAYOUT_MANAGER =
            "VERTICAL_TWO_COLUMN_GRID_LAYOUT_MANAGER"
        const val VERTICAL_THREE_COLUMN_GRID_LAYOUT_MANAGER =
            "VERTICAL_THREE_COLUMN_GRID_LAYOUT_MANAGER"

        const val VERTICAL_LINEAR_LAYOUT_MANAGER = "VERTICAL_LINEAR_LAYOUT_MANAGER"

        const val HORIZONTAL_ONE_COLUMN_GRID_LAYOUT_MANAGER =
            "HORIZONTAL_ONE_COLUMN_GRID_LAYOUT_MANAGER"

        const val SERVING_SIZE_LAYOUT_MANAGER = "SERVING_SIZE_LAYOUT_MANAGER"
    }

    @Provides
    @Named(VERTICAL_ONE_COLUMN_GRID_LAYOUT_MANAGER)
    fun provideVerticalOneColumnLayoutManager(@ActivityContext context: Context) =
        GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)

    @Provides
    @Named(VERTICAL_TWO_COLUMN_GRID_LAYOUT_MANAGER)
    fun provideVerticalTwoColumnLayoutManager(@ActivityContext context: Context) =
        GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

    @Provides
    @Named(VERTICAL_THREE_COLUMN_GRID_LAYOUT_MANAGER)
    fun provideVerticalThreeColumnLayoutManager(@ActivityContext context: Context) =
        GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)

    @Provides
    @Named(VERTICAL_LINEAR_LAYOUT_MANAGER)
    fun provideLayoutManager(@ActivityContext context: Context) =
        LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    @Provides
    @Named(HORIZONTAL_ONE_COLUMN_GRID_LAYOUT_MANAGER)
    fun provideHorizontalOneColumnLayoutManager(@ActivityContext context: Context) =
        GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false)
}