package com.tile.yvesv.nativeappsiproject.model

import java.io.Serializable

/**
 * Single Responsibility Principle: Player is only responsible for knowing its data
 * @param playerData Stores the data for a player
 */
class Player(override val playerData: PlayerData) : Serializable, IPlayer
{
    val name: String
        get() = playerData.name
        /*set(value)
        {
            playerData.name = value
        }*/

    val description: String
        get() = playerData.description

    val text: String
        get() = playerData.text

    val score: Int
        get() = playerData.score
}
