package com.tile.yvesv.nativeappsiproject

import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tile.yvesv.nativeappsiproject.domain.Player
import com.tile.yvesv.nativeappsiproject.domain.PlayerData
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.domain.Comic

class MainActivity : AppCompatActivity(), RageComicListFragment.OnRageComicSelected
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** Checks that the activity doesn’t have saved state.
         * When an activity is saved, all of its active fragments are also saved. If you don’t perform this check
         * you can end up with a whole bunch of fragments */
        if (savedInstanceState == null)
        {
            /**
             * Dynamically add fragment to this activity
             *
             * root_layout in activity_main.xml
             * fragment instance to be added
             * tag/identifier, allows the FragmentManager to later retrieve the fragment
             */
            supportFragmentManager
                    .beginTransaction() //oa mogelijk: add, remove, replace, hide
                    .add(R.id.root_layout, RageComicListFragment.newInstance(), "rageComicList")
                    .commit()
        }
    }

    //invoking the click listener
    override fun onRageComicSelected(comic: Comic) {
        //Toast.makeText(this, "Hey, you selected " + comic.name + "!",
          //      Toast.LENGTH_SHORT).show()
        val detailsFragment = RageComicDetailsFragment.newInstance(comic)
        supportFragmentManager.beginTransaction()
                .replace(R.id.root_layout, detailsFragment, "rageComicDetails")
                .addToBackStack(null)
                .commit()
    }

    /**
     * Starts the Activity
     * - Allocate resources
     * - register click listeners
     * - update UI
     */
    override fun onStart()
    {
        super.onStart()
    }

    private fun createPlayers(): List<Player>
    {
        val playerList = mutableListOf<Player>()

        val resources = applicationContext.resources

        val firstNames = resources.getStringArray(R.array.firstnames)
        val surNames = resources.getStringArray(R.array.surnames)

        for (i in 0 until firstNames.size)
        {
            val playerData = PlayerData(surNames[i], firstNames[i])
            val player = Player(playerData)
            playerList.add(player)
        }

        return playerList
    }
}