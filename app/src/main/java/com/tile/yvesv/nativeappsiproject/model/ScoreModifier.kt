package com.tile.yvesv.nativeappsiproject.model

interface ScoreModifier
{
    fun increaseScoreByOne()
    fun decreaseScoreByOne()
    fun increaseScoreBy(points: Int)
    fun decreaseScoreBy(points: Int)
}