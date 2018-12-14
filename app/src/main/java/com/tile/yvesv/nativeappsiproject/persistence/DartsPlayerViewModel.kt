package com.tile.yvesv.nativeappsiproject.persistence

//import com.tile.yvesv.nativeappsiproject.model.PlayerData
/**
 * anko-commons import for doAsync
 */
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.App
import com.tile.yvesv.nativeappsiproject.model.Player
import javax.inject.Inject

class DartsPlayerViewModel : ViewModel()
{
    init
    {
        App.component.inject(this)
    }
    @Inject
    lateinit var playerRepository: PlayerRepository

    val allPlayers: LiveData<List<Player>> = playerRepository.players

    fun insert(player: Player)
    {

        playerRepository.insert(player)

    }

    fun delete(player: Player)
    {

        playerRepository.delete(player)
    }

    //fun update(player: PlayerData)
    fun update(player: Player)
    {

        playerRepository.update(player)

    }
}
