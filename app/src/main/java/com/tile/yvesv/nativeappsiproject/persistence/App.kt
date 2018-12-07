package com.tile.yvesv.nativeappsiproject.persistence

import android.app.Application
import com.tile.yvesv.nativeappsiproject.injection.component.DaggerDatabaseComponent
import com.tile.yvesv.nativeappsiproject.injection.component.DatabaseComponent
import com.tile.yvesv.nativeappsiproject.injection.module.DatabaseModule

class App : Application()
{
    companion object
    {
        lateinit var component: DatabaseComponent
    }

    override fun onCreate()
    {
        super.onCreate()
        component = DaggerDatabaseComponent
                .builder()
                .databaseModule(DatabaseModule(this))
                .build()
    }
}