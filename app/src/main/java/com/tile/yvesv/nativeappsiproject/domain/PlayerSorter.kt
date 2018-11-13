package com.tile.yvesv.nativeappsiproject.domain

object PlayerSorter
{
    fun SortOnName(players: List<Player>) : List<Player>
    {
        return players.sortedWith(compareBy {
            it.playerData.name
        })
    }

    fun SortOnScoresDesc(players: List<Player>) : List<Player>
    {
        return players.sortedWith(compareByDescending {
            it.playerData.score
        })
    }
}