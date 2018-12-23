package com.tile.yvesv.nativeappsiproject.model

object PlayerSorter : IPlayerSorter
{
    override fun sortOnNameAsc(players: List<Player>) : List<Player>
    {
        return players.sortedWith(compareBy {
            //it.playerData.name
            it.name.toLowerCase()
        })
    }

    override fun sortOnScoreDesc(players: List<Player>) : List<Player>
    {
        return players.sortedWith(compareByDescending {
            //it.playerData.score
            it.score
        })
    }
}