package com.tile.yvesv.nativeappsiproject.gui.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * @class [PlayerViewModel]: Stores MutableLiveData so we can automatically update values in the ui.
 *
 * @property name: MutableLiveData for the name of the player.
 * @property description: MutableLiveData for the description of the player.
 * @property score: MutableLiveData for the score of the player.
 * @property text: MutableLiveData for the text of the player.
 */
class PlayerViewModel : ViewModel()
{
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