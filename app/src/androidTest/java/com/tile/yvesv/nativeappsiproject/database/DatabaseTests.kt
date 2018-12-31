package com.tile.yvesv.nativeappsiproject.database

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.tile.yvesv.nativeappsiproject.TestUtils.getValue
import com.tile.yvesv.nativeappsiproject.model.Player
import com.tile.yvesv.nativeappsiproject.persistence.DartsDao
import com.tile.yvesv.nativeappsiproject.persistence.DartsDatabase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTests
{
    private lateinit var playerDao: DartsDao
    private lateinit var db: DartsDatabase

    @Before
    fun createDatabase()
    {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, DartsDatabase::class.java).build()
        playerDao = db.playerDao()

        val player1 = Player(name = "Yves", description = "Anything", score = 0)
        val player2 = Player(name = "Ernie", description = "Anything", score = 1)

        playerDao.insert(player1)
        playerDao.insert(player2)
    }

    /*@After
    fun deleteAllPlayers()
    {
        playerDao.deleteAll()
    }*/

    @Test
    fun insertPlayers_areInserted()
    {
        val playerNew = Player(name = "Bert", description = "Anything", score = 1)

        playerDao.insert(playerNew)
        assertEquals(3, getValue(playerDao.getAllPlayers()).size)
    }

    @Test
    fun deletePlayer_removesOne()
    {
        playerDao.delete(getValue(playerDao.getAllPlayers())[0])
        assertEquals(1, getValue(playerDao.getAllPlayers()).size)
    }

    @Test
    fun deleteAll_removesAll()
    {
        playerDao.deleteAll()
        assertEquals(0, getValue(playerDao.getAllPlayers()).size)
    }

    @Test
    fun updatePlayer_updates()
    {
        var player1 = Player(name = "Yves", description = "Anything", score = 0)
        playerDao.insert(player1)

        player1 = getValue(playerDao.getAllPlayers())[0]
        player1.name = "YvesAltered"

        playerDao.update(player1)

        assertEquals("YvesAltered", getValue(playerDao.getAllPlayers())[0].name)
    }
}
