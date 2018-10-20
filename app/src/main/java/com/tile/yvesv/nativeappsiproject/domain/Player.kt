package com.tile.yvesv.nativeappsiproject.domain

import java.io.Serializable

data class Player(val imageResId: Int, val name: String, val description: String, var text: String = "") : Serializable
