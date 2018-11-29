package com.tile.yvesv.nativeappsiproject.injection.component

import com.tile.yvesv.nativeappsiproject.gui.viewmodels.BoredActivityViewModel
import com.tile.yvesv.nativeappsiproject.injection.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing the inject functions for presenters.
 *
 * A component is a mapping between one or more modules and one or more classes that will use them.
 * In this case we have the [NetworkModule] which needs to inject dependencies in our [BoredActivityViewModel]
 *
 * https ://google.github.io/dagger/api/2.14/dagger/Component.html
 */

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjectorComponent {


    /**
     * Injects the dependencies into the specified [BoredActivityViewModel].
     * @param boredActivityViewModel the [BoredActivityViewModel] in which to inject the dependencies.
     */
    fun inject(boredActivityViewModel: BoredActivityViewModel)


    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjectorComponent

        fun networkModule(networkModule: NetworkModule): Builder
    }

}