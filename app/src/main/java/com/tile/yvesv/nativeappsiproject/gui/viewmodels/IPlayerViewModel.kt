package com.tile.yvesv.nativeappsiproject.gui.viewmodels

interface IPlayerViewModel
{
    fun increaseScoreByOne()
    fun decreaseScoreByOne()
    fun increaseScoreBy(points: Int)
    fun decreaseScoreBy(points: Int)
    fun resetScore(score: Int)
}