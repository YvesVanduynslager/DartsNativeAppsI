package com.tile.yvesv.nativeappsiproject.model

interface IScoreModifier
{
    fun increaseScoreByOne()
    fun decreaseScoreByOne()
    fun increaseScoreBy(points: Int)
    fun decreaseScoreBy(points: Int)
    fun resetScore(score: Int)
}