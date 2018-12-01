package com.tile.yvesv.nativeappsiproject.model

import java.io.Serializable

/**
 * @class Stores the properties for the player
 * @param imageResId the id of the player's image
 * @param name the player's name
 * @param description description of the player
 */
data class PlayerData(val imageResId: Int, val name: String, var description: String, var score: Int, var text: String = "") : Serializable
