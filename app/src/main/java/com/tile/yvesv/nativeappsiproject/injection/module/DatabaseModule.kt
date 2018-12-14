package com.tile.yvesv.nativeappsiproject.injection.module

import android.app.Application
import android.content.Context
import com.tile.yvesv.nativeappsiproject.persistence.DartsDatabase
import com.tile.yvesv.nativeappsiproject.persistence.DartsDao
import com.tile.yvesv.nativeappsiproject.persistence.PlayerRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module which provides all required dependencies for the network.
 *
 * Object: Singleton instance (https://kotlinlang.org/docs/reference/object-declarations.html)
 *
 * Methods annotated with @Provides informs Dagger that this method is the constructor
 */
@Module
class DatabaseModule(private val application: Application)
{
    @Provides
    @Singleton
    internal fun providePlayerRepository(dartsDao: DartsDao): PlayerRepository
    {
        return PlayerRepository(dartsDao)
    }

    @Provides
    @Singleton
    internal fun providePlayerDao(dartsDatabase: DartsDatabase): DartsDao
    {
        return dartsDatabase.playerDao()
    }

    @Provides
    @Singleton
    internal fun provideDartsDatabase(context: Context): DartsDatabase
    {
        return DartsDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context
    {
        return application
    }
}