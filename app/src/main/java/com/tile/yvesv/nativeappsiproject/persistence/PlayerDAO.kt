package com.tile.yvesv.nativeappsiproject.persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.tile.yvesv.nativeappsiproject.model.PlayerData

@Dao
interface PlayerDAO
{
    /**
     * Use the @Query annotation to specify a custom SQL query.
     * By specifying LiveData as the return value Room will provide all the necessary code
     * to update the LiveData object when the database is updated. (niceeeee)
     */

    /**
     * Get all players
     */
    @Query("SELECT * from player_data")
    fun getAllPlayers(): LiveData<List<PlayerData>>

    /**
     * Insert player by model
     */
    @Insert
    fun insert(player_data: PlayerData)

    /**
     * Delete all players
     */
    @Query("DELETE FROM player_data")
    fun deleteAll()

    /**
     * Delete player by name
     */
    @Query("DELETE FROM player_data WHERE player_name = :name")
    fun deleteByPlayerName(name: String)

    /**
     * Delete player by model
     */
    @Delete
    fun delete(data: PlayerData)

    /**
     * Update name by id
     */
    @Query("UPDATE player_data SET player_name= :name WHERE id= :id")
    fun update(name:String, id: Int);

    /**
     * Update score by id
     */
    @Query("UPDATE player_data SET player_score=:score WHERE id= :id")
    fun update(score: Int, id: Int)

    /**
     * Update by model
     */
    @Update
    fun update(playerData: PlayerData);
}