package com.tile.yvesv.nativeappsiproject.persistence

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.model.PlayerData
import javax.inject.Inject
/**
 * anko-commons import for doAsync
 */
import org.jetbrains.anko.*

class DaoViewModel : ViewModel()
{
    @Inject
    lateinit var playerRepository: PlayerRepository

    init {
        App.component.inject(this)
    }

    val allPlayers: LiveData<List<PlayerData>> = playerRepository.players

    fun insert(player: PlayerData)
    {
        doAsync {
            playerRepository.create(player)
        }
    }

    fun delete(player: PlayerData)
    {
        doAsync {
            playerRepository.delete(player)
        }
    }

    fun update(player: PlayerData)
    {
        doAsync {
            playerRepository.update(player)
        }
    }
}
