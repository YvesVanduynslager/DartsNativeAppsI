package com.tile.yvesv.nativeappsiproject.persistence

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
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
        Log.i("CRUDoperation", "Inserted $player")
    }

    @WorkerThread
    fun delete(player: Player)
    {
        doAsync { dartsDao.delete(player) }
        Log.i("CRUDoperation", "Deleted $player")
    }

    @WorkerThread
    fun update(player: Player)
    {
        doAsync { dartsDao.update(player) }
        Log.i("CRUDoperation", "Updated $player")
    }
}