package com.tile.yvesv.nativeappsiproject.injection.component

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.BoredActivityViewModel
import com.tile.yvesv.nativeappsiproject.injection.module.DatabaseModule
import com.tile.yvesv.nativeappsiproject.injection.module.NetworkModule
import com.tile.yvesv.nativeappsiproject.persistence.DaoViewModel
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
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {


    /**
     * Injects the dependencies into the specified [DaoViewModel].
     * @param daoViewModel the [DaoViewModel] in which to inject the dependencies.
     */
    fun inject(daoViewModel: ViewModel) /*Solved issue with DaggerDatabaseComponent not found
    by changing DaoViewModel to ViewModel. Same principle for ViewModelInjectorComponent*/


    @Component.Builder
    interface Builder {
        fun build(): DatabaseComponent

        fun databaseModule(databaseModule: DatabaseModule): Builder
    }

}