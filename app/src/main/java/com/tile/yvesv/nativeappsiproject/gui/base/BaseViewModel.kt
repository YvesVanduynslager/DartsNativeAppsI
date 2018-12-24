package com.tile.yvesv.nativeappsiproject.gui.base

import android.arch.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.BoredActivityViewModel
import com.tile.yvesv.nativeappsiproject.injection.component.DaggerViewModelInjectorComponent
import com.tile.yvesv.nativeappsiproject.injection.component.ViewModelInjectorComponent
import com.tile.yvesv.nativeappsiproject.injection.module.NetworkModule

/**
 * Base class for the ViewModels, as we will use it for dependency injection only.
 */
abstract class BaseViewModel : ViewModel()
{
    private val injectorComponent: ViewModelInjectorComponent = DaggerViewModelInjectorComponent
            .builder()
            .networkModule(NetworkModule)
            .build()

    init
    {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject()
    {
        when (this)
        {
            is BoredActivityViewModel -> injectorComponent.inject(this)
        }
    }
}