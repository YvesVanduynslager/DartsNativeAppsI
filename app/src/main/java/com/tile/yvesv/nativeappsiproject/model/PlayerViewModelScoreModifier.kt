package com.tile.yvesv.nativeappsiproject.model

import android.arch.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.IPlayerViewModel
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.PlayerViewModel

/**
 * @class [PlayerViewModelScoreModifier]:
 * This class changes the value of the score for the selected player
 * Logic happens here instead of PlayerViewModel to adhere to Single Responsibility Principle
 * and Dependency Inversion Principle
 *
 * @param playerViewModel the view model for the selected player that needs its score adjusted.
 *
 * @author Yves Vanduynslager
 */
class PlayerViewModelScoreModifier(private val playerViewModel: IPlayerViewModel) : IPlayerScoreModifier
{
    /**
     * Increase the score with 1.
     */
    override fun increaseScoreByOne()
    {
        playerViewModel.increaseScoreByOne()

        /*this.playerViewModel.score.let {
            var score = it.value!!
            score += 1
            it.value = score
        }*/
    }

    /**
     * Decrease the score with 1.
     * @throws ZeroException: on adjusting score below 0.
     */
    override fun decreaseScoreByOne()
    {
        playerViewModel.decreaseScoreByOne()

        /*this.playerViewModel.score.let{
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
        }*/
    }

    /**
     * Increase the score with [points].
     * @param points the number of points to be increased.
     */
    override fun increaseScoreBy(points: Int)
    {
        this.playerViewModel.increaseScoreBy(points)
    }

    /**
     * Decrease the score with [points]
     * @param points the number of points to be decreased.
     */
    override fun decreaseScoreBy(points: Int)
    {
        this.playerViewModel.decreaseScoreBy(points)

    }
}