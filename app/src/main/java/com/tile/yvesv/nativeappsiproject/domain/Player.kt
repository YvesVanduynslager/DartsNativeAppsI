package com.tile.yvesv.nativeappsiproject.domain

import java.io.Serializable

data class Player(val playerData: PlayerData) : Serializable
{
    fun plusOneToScore()
    {
        playerData.score += 1
    }

    fun minusOneToScore()
    {
        playerData.score -= 1
    }
}
