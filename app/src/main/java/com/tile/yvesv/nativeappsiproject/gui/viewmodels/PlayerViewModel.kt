package com.tile.yvesv.nativeappsiproject.gui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException

/**
 * @class [PlayerViewModel]: Stores MutableLiveData so we can automatically update values in the ui.
 *
 * @property name: MutableLiveData for the name of the player.
 * @property description: MutableLiveData for the description of the player.
 * @property score: MutableLiveData for the score of the player.
 * @property text: MutableLiveData for the text of the player.
 */
class PlayerViewModel : ViewModel(), IPlayerViewModel
{
    /**
     * Wrap in MutableLiveData to automatically update the ui on state changes
     * view that uses the view model needs to be initialized using ViewModelProviders
     */
    var name = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var score = MutableLiveData<Int>()
    var text = MutableLiveData<String>()

    init //Not needed to init these, are initialized in gui with Player properties
    {
        //name.value = ""
        //description.value= ""
        //score.value = 0
        //text.value = ""
    }

    /**
     * Increase the score with 1.
     */
    override fun increaseScoreByOne()
    {
        this.score.let {
            var score = it.value!!
            score += 1
            it.value = score
        }
    }

    /**
     * Decrease the score with 1.
     * @throws ZeroException: on adjusting score below 0.
     */
    override fun decreaseScoreByOne()
    {
        this.score.let{
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

    /**
     * Increase the score with [points].
     * @param points the number of points to be increased.
     */
    override fun increaseScoreBy(points: Int)
    {
        this.score.let {
            var score = it.value!!
            score += points
            it.value = score
        }
    }

    /**
     * Decrease the score with [points]
     * @param points the number of points to be decreased.
     *
     * @throws ZeroException: on adjusting score below 0.
     */
    override fun decreaseScoreBy(points: Int)
    {
        this.score.let {
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

    /**
     * Reset the score for the viewModel.
     *
     * @param score score that needs to be reset to.
     */
    override fun resetScore(score: Int)
    {
        this.score.value = score
    }
}