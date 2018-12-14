package com.tile.yvesv.nativeappsiproject.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Single Responsibility Principle: Player is only responsible for knowing its data
 * @param playerData Stores the data for a player
 */
/*class Player(override val playerData: PlayerData) : Serializable, IPlayer
{
    val name: String
        get() = playerData.name
        /*set(value)
        {
            playerData.name = value
        }*/

    val description: String
        get() = playerData.description

    val text: String
        get() = playerData.text

    val score: Int
        get() = playerData.score
}*/
/**
 * @class Stores the properties for the player
 * @param name the player's name
 * @param description description of the player
 */
@Entity(tableName = "player_data")
data class Player(@PrimaryKey(autoGenerate = true) var id: Int = 0,
             @ColumnInfo(name="player_name") val name: String,
             @ColumnInfo(name="player_descr") var description: String,
             @ColumnInfo(name="player_score") var score: Int,
             @ColumnInfo(name="extra_text") var text: String = ""): Serializable, IPlayer
{

}
