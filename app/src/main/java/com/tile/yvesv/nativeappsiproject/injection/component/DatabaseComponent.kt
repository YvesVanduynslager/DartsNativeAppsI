package com.tile.yvesv.nativeappsiproject.injection.component

import android.arch.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.injection.module.DatabaseModule
import com.tile.yvesv.nativeappsiproject.App
import com.tile.yvesv.nativeappsiproject.persistence.DartsPlayerViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent
{
    fun inject(app: App)
    fun inject(dartsPlayerViewModel: DartsPlayerViewModel)
}