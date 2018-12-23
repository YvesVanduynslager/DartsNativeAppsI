package com.tile.yvesv.nativeappsiproject.model

interface IPlayerScoreModifier
{
    fun increaseScoreByOne()
    fun decreaseScoreByOne()
    fun increaseScoreBy(points: Int)
    fun decreaseScoreBy(points: Int)
}