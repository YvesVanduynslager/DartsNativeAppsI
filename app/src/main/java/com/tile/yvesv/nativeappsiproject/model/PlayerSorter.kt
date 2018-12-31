package com.tile.yvesv.nativeappsiproject.model

/**
 * @object [PlayerSorter]: Sorting for a player list.
 *
 * @author Yves Vanduynslager
 */
object PlayerSorter : IPlayerSorter
{
    /**
     * Sorts on name ascending
     * @param players: player list that needs sorting.
     * @return Sorted player list
     */
    override fun sortOnNameAsc(players: List<Player>) : List<Player>
    {
        return players.sortedWith(compareBy {
            it.name.toLowerCase()
        })
    }

    /**
     * Sorts on score descending
     * @param players: player list that needs sorting.
     * @return Sorted player list
     */
    override fun sortOnScoreDesc(players: List<Player>) : List<Player>
    {
        return players.sortedWith(compareByDescending {
            it.score
        })
    }
}