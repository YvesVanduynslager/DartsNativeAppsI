package com.tile.yvesv.nativeappsiproject.model

import java.io.Serializable

/**
 * Single Responsibility Principle: Player is only responsible for knowing its data
 * @param playerData Stores the data for a player
 */
class Player(override val playerData: PlayerData) : Serializable, IPlayer
{
    val imageResId: Int
        get() = playerData.imageResId

    val name: String
        get() = playerData.name

    val description: String
        get() = playerData.description

    val text: String
        get() = playerData.text

    val score: Int
        get() = playerData.score
}
