package org.fealous.theseed.di

import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions3.RxPermissions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Named

@InstallIn(FragmentComponent::class)
@Module
class PermissionModule {
    companion object {
        const val FRAGMENT_RX_PERMISSION = "FRAGMENT_RX_PERMISSION"
    }

    @Provides
    @Named(FRAGMENT_RX_PERMISSION)
    fun provideRxPermission(fragment: Fragment) = RxPermissions(fragment)
}