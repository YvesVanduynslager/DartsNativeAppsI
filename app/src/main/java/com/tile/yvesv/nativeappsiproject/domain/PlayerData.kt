package com.tile.yvesv.nativeappsiproject.domain

import java.io.Serializable

data class PlayerData(val imageResId: Int, val name: String, var description: String, var score: Int, var text: String = "") : Serializable
