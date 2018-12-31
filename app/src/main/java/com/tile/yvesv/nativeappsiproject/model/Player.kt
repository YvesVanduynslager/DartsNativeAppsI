package com.tile.yvesv.nativeappsiproject.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException
import java.io.Serializable

/**
 * @class [Player]: Stores the properties for the player
 *
 * @param id: player id
 * @param name the player's name
 * @param description description of the player
 * @param score: the player's score
 * @param text: extra text
 *
 * @author Yves Vanduynslager
 * */
@Entity(tableName = "player_data")
data class Player(@PrimaryKey(autoGenerate = true) var id: Int = 0,
             @ColumnInfo(name="player_name") var name: String,
             @ColumnInfo(name="player_descr") var description: String,
             @ColumnInfo(name="player_score") var score: Int,
             @ColumnInfo(name="extra_text") var text: String = ""): Serializable, IPlayer
{
    fun saveScore(score: Int)
    {
        if(score < 0)
            throw ZeroException("Score can not be lower than 0!")

        this.score = score
    }
}
