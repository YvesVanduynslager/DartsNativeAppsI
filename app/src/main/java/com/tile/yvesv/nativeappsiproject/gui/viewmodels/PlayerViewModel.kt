package com.tile.yvesv.nativeappsiproject.gui.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException
import java.io.File

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
    override fun increaseScoreByOne()
    {
        this.score.let {
            var score = it.value!!
            score += 1
            it.value = score
        }
    }

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

    override fun increaseScoreBy(points: Int)
    {
        this.score.let {
            var score = it.value!!
            score += points
            it.value = score
        }
    }

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
     * Wrap in MutableLiveData to automatically update the ui on state changes
     * view that uses the view model needs to be initialized using ViewModelProviders
     */
    var name = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var score = MutableLiveData<Int>()
    var text = MutableLiveData<String>()

    init
    {
        //imageResId.value = -1
        //name.value = ""
        //description.value= ""
        //score.value = 0
        //text.value = ""
    }


}