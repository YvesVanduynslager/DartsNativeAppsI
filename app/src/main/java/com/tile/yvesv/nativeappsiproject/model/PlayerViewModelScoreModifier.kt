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
    }

    /**
     * Decrease the score with 1.
     */
    override fun decreaseScoreByOne()
    {
        playerViewModel.decreaseScoreByOne()
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

    override fun resetScore(score: Int)
    {
        this.playerViewModel.resetScore(score)
    }
}