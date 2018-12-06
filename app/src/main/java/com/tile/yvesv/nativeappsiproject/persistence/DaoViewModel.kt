package com.tile.yvesv.nativeappsiproject.persistence

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tile.yvesv.nativeappsiproject.model.PlayerData
import javax.inject.Inject

class DaoViewModel : ViewModel()
{
    @Inject
    lateinit var playerRepository: PlayerRepository

}
