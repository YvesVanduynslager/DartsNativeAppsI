package com.tile.yvesv.nativeappsiproject.domain

interface ScoreModifier
{
    fun increaseScoreByOne()
    fun decreaseScoreByOne()
    fun increaseScoreBy(points: Int)
    fun decreaseScoreBy(points: Int)
}