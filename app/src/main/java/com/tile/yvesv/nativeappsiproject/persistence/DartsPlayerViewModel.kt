package com.tile.yvesv.nativeappsiproject.persistence

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.App
import com.tile.yvesv.nativeappsiproject.model.Player
import javax.inject.Inject

/**
 * @class [DartsPlayerViewModel]: CRUD operations on the database.
 */
class DartsPlayerViewModel : ViewModel()
{
    init
    {
        App.component.inject(this)
    }

    @Inject
    lateinit var playerRepository: PlayerRepository

    /**
     * READ
     */
    val allPlayers: LiveData<List<Player>> = playerRepository.players

    /**
     * CREATE
     * @param player: player to be inserted.
     */
    fun insert(player: Player)
    {
        playerRepository.insert(player)
    }

    /**
     * DELETE
     * @param player: player to be deleted.
     */
    fun delete(player: Player)
    {
        playerRepository.delete(player)
    }

    /**
     * UPDATE
     * @param player: player to be updated
     */
    fun update(player: Player)
    {
        playerRepository.update(player)
    }
}
