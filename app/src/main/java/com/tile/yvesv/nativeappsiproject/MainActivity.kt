package com.tile.yvesv.nativeappsiproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tile.yvesv.nativeappsiproject.domain.Player

class MainActivity : AppCompatActivity(), PlayerListFragment.OnPlayerSelected
{
    var selectedPlayerId: Int = -1

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
                    .add(R.id.root_layout, PlayerListFragment.newInstance(), "playerList")
                    .commit()
        }
    }

    //invoking the click listener
    override fun onPlayerSelected(player: Player)
    {
        //Toast.makeText(this, "Hey, you selected " + player.name + "!",
        //      Toast.LENGTH_SHORT).show()
        val detailsFragment = PlayerDetailsFragment.newInstance(player)
        supportFragmentManager.beginTransaction()
                .replace(R.id.root_layout, detailsFragment, "playerDetails") //hier kan je bvb add doen ipv replace in een andere countainer
                .addToBackStack(null)
                .commit()

        //this.selectedPlayerId = player.playerData.id
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
}