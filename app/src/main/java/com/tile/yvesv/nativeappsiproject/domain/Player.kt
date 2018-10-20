package com.tile.yvesv.nativeappsiproject.domain

import android.util.Log
import java.io.Serializable

class Player(val playerData: PlayerData) : Serializable, IPlayer
{
    override fun increaseScoreByOne()
    {
        playerData.score += 1
        Log.i("plus one called in ", "${this.javaClass}")
    }

    override fun decreaseScoreByOne()
    {
        playerData.score -= 1
        Log.i("minus one called in ", "${this.javaClass}")
    }

    override fun increaseScoreBy(points: Int)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decreaseScoreBy(points: Int)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
