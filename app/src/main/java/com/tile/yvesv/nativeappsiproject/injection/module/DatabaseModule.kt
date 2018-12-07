package com.tile.yvesv.nativeappsiproject.injection.module

import android.app.Application
import android.content.Context
import com.tile.yvesv.nativeappsiproject.persistence.DartsDatabase

import dagger.Module
import dagger.Provides

/**
 * Module which provides all required dependencies for the network.
 *
 * Object: Singleton instance (https://kotlinlang.org/docs/reference/object-declarations.html)
 *
 * Methods annotated with @Provides informs Dagger that this method is the constructor
 */
@Module
class DatabaseModule(private var application: Application)
{
    @Provides
    fun provideContext() : Context
    {
        return application
    }

    @Provides
    fun provideDatabase(context: Context) : DartsDatabase
    {
        return DartsDatabase.getDatabase(context)
    }
}