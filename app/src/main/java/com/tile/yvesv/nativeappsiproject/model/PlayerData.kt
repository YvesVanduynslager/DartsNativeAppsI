package com.tile.yvesv.nativeappsiproject.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * @class Stores the properties for the player
 * @param name the player's name
 * @param description description of the player
 */
@Entity(tableName = "player_data")
data class PlayerData1(
        @PrimaryKey(autoGenerate = true) val id: Int,
        @ColumnInfo(name="player_name") val name: String,
        @ColumnInfo(name="player_descr") var description: String,
        @ColumnInfo(name="player_score") var score: Int,
        @ColumnInfo(name="extra_text") var text: String = "") : Serializable
