package com.tile.yvesv.nativeappsiproject.model

interface IPlayerSorter
{
    fun sortOnNameAsc(players: List<Player>) : List<Player>
    fun sortOnScoreDesc(players: List<Player>) : List<Player>
}