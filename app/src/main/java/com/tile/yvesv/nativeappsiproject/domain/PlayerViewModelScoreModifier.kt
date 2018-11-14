package com.tile.yvesv.nativeappsiproject.domain

import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException
import com.tile.yvesv.nativeappsiproject.gui.PlayerViewModel

class PlayerViewModelScoreModifier(private val playerViewModel: PlayerViewModel) : ScoreModifier
{
    override fun increaseScoreByOne()
    {
        this.playerViewModel.score.let {
            var score = it.value!!
            score += 1
            it.value = score
        }
    }
    override fun decreaseScoreByOne()
    {
        this.playerViewModel.score.let{
            var score = it.value!!
            if(score > 0)
            {
                score -= 1
                it.value = score
            }
            else
            {
                //use resources here
                throw ZeroException("Score can not be lower than 0!")
            }
        }
    }

    override fun increaseScoreBy(points: Int)
    {
        this.playerViewModel.score.let {
            var score = it.value!!
            score += points
            it.value = score
        }
    }
    override fun decreaseScoreBy(points: Int)
    {
        this.playerViewModel.score.let {
            var score = it.value!!
            if(score - points > 0)
            {
                score -= points
                it.value = score
            }
            else
            {
                throw ZeroException("Score can not be lower than 0!")
            }
        }
    }
}