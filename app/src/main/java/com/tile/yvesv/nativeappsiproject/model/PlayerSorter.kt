package com.tile.yvesv.nativeappsiproject.model

object PlayerSorter
{
    fun sortOnNameAsc(players: List<Player>) : List<Player>
    {
        return players.sortedWith(compareBy {
            //it.playerData.name
            it.name.toLowerCase()
        })
    }

    fun sortOnScoreDesc(players: List<Player>) : List<Player>
    {
        return players.sortedWith(compareByDescending {
            //it.playerData.score
            it.score
        })
    }
}