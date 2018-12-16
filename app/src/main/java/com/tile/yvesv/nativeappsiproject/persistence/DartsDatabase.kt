package com.tile.yvesv.nativeappsiproject.persistence

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.tile.yvesv.nativeappsiproject.model.Player
import okhttp3.internal.Internal.instance
import org.jetbrains.anko.doAsync

//import com.tile.yvesv.nativeappsiproject.model.PlayerData

//@Database(entities = [PlayerData::class], version = 1, exportSchema = false)
/**
 * We use @Database to annotate the class that represents the database itself.
 * In the annotation we also specify all the entities that will be saved in this database.
 *
 * In order to keep track of different db schemas over time we also have to provide a version.
 * For now we can just destroy and recreate the database when a new schema is needed.
 */
@Database(entities = [Player::class], version = 1, exportSchema = false)
abstract class DartsDatabase : RoomDatabase()
{
    abstract fun playerDao(): DartsDao

    companion object
    {
        @Volatile
        private var INSTANCE: DartsDatabase? = null
        private const val DATABASE_NAME = "Darts_database"

        fun getDatabase(context: Context): DartsDatabase
        {
            val tempInstance = INSTANCE
            if (tempInstance != null)
            {
                return tempInstance
            }

            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DartsDatabase::class.java,
                        DATABASE_NAME)
                        .addCallback(object : RoomDatabase.Callback()
                        {
                            override fun onOpen(db: SupportSQLiteDatabase)
                            {
                                super.onOpen(db)
                                doAsync {
                                    populateDatabase(INSTANCE!!.playerDao())
                                }
                            }
                        }).build()

                INSTANCE = instance
                return instance
            }
        }

        fun populateDatabase(dartsDao: DartsDao)
        {
            if (dartsDao.getPlayerCount() == 0)
            {
                var player = Player(name = "Ana", description = "Main healer", score = 0)
                dartsDao.insert(player)

                player = Player(name = "Lucio", description = "Off healer", score = 1)
                dartsDao.insert(player)

                player = Player(name = "Moira", description = "Main healer", score = 1)
                dartsDao.insert(player)

                player = Player(name = "Tracer", description = "DPS", score = 2)
                dartsDao.insert(player)

                player = Player(name = "Widowmaker", description = "DPS", score = 3)
                dartsDao.insert(player)

                player = Player(name = "Zenyatta", description = "Off healer", score = 5)
                dartsDao.insert(player)

                player = Player(name = "Ashe", description = "DPS", score = 8)
                dartsDao.insert(player)

                player = Player(name = "Roadhog", description = "Off tank", score = 13)
                dartsDao.insert(player)

                player = Player(name = "Reinhardt", description = "Main tank", score = 21)
                dartsDao.insert(player)

                player = Player(name = "Junkrat", description = "DPS", score = 34)
                dartsDao.insert(player)

                player = Player(name = "Winston", description = "Main tank", score = 55)
                dartsDao.insert(player)

                player = Player(name = "Zarya", description = "Off tank", score = 89)
                dartsDao.insert(player)
            }
        }
    }
}