package com.tile.yvesv.nativeappsiproject.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.tile.yvesv.nativeappsiproject.model.PlayerData

@Database(entities = [PlayerData::class], version = 1, exportSchema = false)
abstract class DartsDatabase : RoomDatabase()
{
    abstract fun playerDao(): PlayerDAO

    //SINGLETON to ensure only one connection to db at a time
    companion object
    {
        @Volatile
        private var INSTANCE: DartsDatabase? = null

        fun getDatabase(context: Context): DartsDatabase
        {
            val tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }

            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DartsDatabase::class.java,
                        "Darts_database").build()

                INSTANCE = instance
                return instance
            }
        }
    }
}