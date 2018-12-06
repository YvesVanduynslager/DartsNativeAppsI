package com.tile.yvesv.nativeappsiproject.persistence

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.tile.yvesv.nativeappsiproject.model.PlayerData

/**
 * @class [PlayerRepository]
 * A Repository class abstracts access to multiple data sources. When a
 * network connection is unavailable, the Dao can be used to request cached data instead.
 * Using the repository pattern hides this complexity from other classes.
 */
class PlayerRepository(private val playerDAO: PlayerDAO)
{
    val players: LiveData<List<PlayerData>> = playerDAO.getAllPlayers()

    @WorkerThread
    fun create(playerData: PlayerData)
    {
        playerDAO.insert(playerData)
    }

    @WorkerThread
    fun delete(playerData: PlayerData)
    {
        playerDAO.delete(playerData)
    }

    @WorkerThread
    fun update(playerData: PlayerData)
    {
        playerDAO.update(playerData)
    }

    /*@WorkerThread
    fun readByName(name: String)
    {
        playerDAO.readByName(name)
    }

    @WorkerThread
    fun readById(id: Int)
    {
        playerDAO.readById(id)
    }*/
}