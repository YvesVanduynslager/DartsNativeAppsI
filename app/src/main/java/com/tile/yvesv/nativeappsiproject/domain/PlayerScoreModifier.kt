package com.tile.yvesv.nativeappsiproject.domain

class PlayerScoreModifier(var player: IPlayer) : ScoreModifier
{
    override fun increaseScoreByOne()
    {
        player.playerData.score += 1
    }
    override fun decreaseScoreByOne()
    {
        if(player.playerData.score > 0)
        {
            player.playerData.score -= 1
        }
        else
        {
            throw Exception("Score can not be lower than 0!")
        }
    }

    override fun increaseScoreBy(points: Int)
    {
        player.playerData.score += points
    }
    override fun decreaseScoreBy(points: Int)
    {
        if(player.playerData.score - points > 0)
        {
            player.playerData.score -= points
        }
        else
        {
            throw Exception("Score can not be lower than 0!")
        }
    }
}