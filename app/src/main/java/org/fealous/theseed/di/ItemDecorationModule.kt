package org.fealous.theseed.di

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Named

@InstallIn(FragmentComponent::class)
@Module
class ItemDecorationModule {
    companion object {
        const val DIVIDER_ITEM_DECORATION = "DIVIDER_ITEM_DECORATION"
    }

    @Provides
    @Named(DIVIDER_ITEM_DECORATION)
    fun provideDividerItemDecoration(@ActivityContext context: Context) = DividerItemDecoration(context, RecyclerView.VERTICAL)
}