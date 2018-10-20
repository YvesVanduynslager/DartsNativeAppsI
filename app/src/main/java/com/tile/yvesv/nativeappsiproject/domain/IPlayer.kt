package com.tile.yvesv.nativeappsiproject.domain

interface IPlayer
{
    fun increaseScoreByOne()
    fun decreaseScoreByOne()
    fun increaseScoreBy(points: Int)
    fun decreaseScoreBy(points: Int)

}