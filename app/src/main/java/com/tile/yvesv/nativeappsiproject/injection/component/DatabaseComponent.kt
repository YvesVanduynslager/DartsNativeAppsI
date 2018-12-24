package com.tile.yvesv.nativeappsiproject.injection.component

import com.tile.yvesv.nativeappsiproject.App
import com.tile.yvesv.nativeappsiproject.injection.module.DatabaseModule
import com.tile.yvesv.nativeappsiproject.persistence.DartsPlayerViewModel
import dagger.Component
import javax.inject.Singleton


/**
 * @class [DatabaseComponent]:
 * Component providing the inject functions for presenters.
 *
 * A component is a mapping between one or more modules and one or more classes that will use them.
 * In this case we have the [DatabaseModule] which needs to inject dependencies in our [DartsPlayerViewModel]
 *
 * https ://google.github.io/dagger/api/2.14/dagger/Component.html
 */
@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent
{
    fun inject(app: App)
    fun inject(dartsPlayerViewModel: DartsPlayerViewModel)
}