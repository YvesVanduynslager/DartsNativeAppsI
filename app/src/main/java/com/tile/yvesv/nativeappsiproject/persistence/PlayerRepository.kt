package com.tile.yvesv.nativeappsiproject.persistence

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import android.util.Log
import com.tile.yvesv.nativeappsiproject.model.Player
import org.jetbrains.anko.doAsync

/**
 * @class [PlayerRepository]
 * A Repository class abstracts access to multiple data sources. When a
 * network connection is unavailable, the Dao can be used to request cached data instead.
 * Using the repository pattern hides this complexity from other classes.
 */
class PlayerRepository(private val dartsDao: DartsDao)
{
    val players: LiveData<List<Player>> = dartsDao.getAllPlayers()

    @WorkerThread
    //fun create(playerData: PlayerData)
    fun insert(player: Player)
    {
        doAsync { dartsDao.insert(player) }
        Log.e("CRUD", "Inserted $player")
    }

    @WorkerThread
    fun delete(player: Player)
    {
        doAsync { dartsDao.delete(player) }
        Log.e("CRUD", "Deleted $player")
    }

    @WorkerThread
    fun update(player: Player)
    {
        doAsync { dartsDao.update(player) }
        Log.e("CRUD", "Updated $player")
    }

    /*@WorkerThread
    fun readByName(name: String)
    {
        dartsDao.readByName(name)
    }

    @WorkerThread
    fun readById(id: Int)
    {
        dartsDao.readById(id)
    }*/
}