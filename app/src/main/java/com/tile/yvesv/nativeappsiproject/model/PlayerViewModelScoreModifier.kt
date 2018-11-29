package com.tile.yvesv.nativeappsiproject.model

import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.PlayerViewModel

/**
 * This class changes the value of the score for the selected player
 * Logic happens here instead of PlayerViewModel to adhere to Single Responsibility Principle
 * and Dependency Inversion Principle
 *
 * @param playerViewModel the view model for the selected player that needs its score adjusted.
 */
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
            else //if the score will be lower than 0, throw a ZeroException and catch it in UI
            {
                //TODO: use resources here
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
            else //if the score will be lower than 0, throw a ZeroException and catch it in UI
            {
                //TODO: use resources here
                throw ZeroException("Score can not be lower than 0!")
            }
        }
    }
}