package com.tile.yvesv.nativeappsiproject.gui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class PlayerViewModel : ViewModel()
{
    val imageResId = MutableLiveData<Int>()
    var name = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var score = MutableLiveData<Int>()
    var text = MutableLiveData<String>()

    init
    {
        imageResId.value = -1
        name.value = ""
        description.value= ""
        score.value = 0
        text.value = ""
    }
}