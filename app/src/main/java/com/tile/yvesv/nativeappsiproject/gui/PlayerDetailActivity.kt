package com.tile.yvesv.nativeappsiproject.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.domain.IPlayer
import com.tile.yvesv.nativeappsiproject.domain.Player

/**
 * An activity representing a single player detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MainActivity].
 */
class PlayerDetailActivity : AppCompatActivity(), PlayerDetailsFragment.DetailFragmentListener
{
    /**
     * Creates the Activity
     * - dependencies (none)
     * - restore saved state (none)
     * - set view
     * - restore state
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        //setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null)
        {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            val detailFragment = PlayerDetailsFragment.newInstance(
                    intent.getSerializableExtra(PlayerDetailsFragment.PLAYER) as IPlayer
            )

            supportFragmentManager.beginTransaction()
                    .add(R.id.player_detail_container, detailFragment)
                    .commit()
        }
    }

    override fun notifyChange(player: IPlayer, vm: PlayerViewModel)
    {
        //Toast.makeText(this, "Player's score hasn't been saved yet: ${player.playerData.score}", Toast.LENGTH_LONG).show()
        Log.d("PLAYER_SCORE", "Score in player object is: ${player.playerData.score}")
        Log.d("PLAYER_VIEW_MODEL_SCORE", "Score in viewmodel is: ${vm.score.value}")
    }

    companion object
    {
        fun newIntent ( context : Context): Intent
        {
            return Intent ( context , PlayerDetailActivity::class.java)
        }
    }

    /**
     * Starts the Activity
     * - Allocate resources
     * - register click listeners
     * - update UI
     */
    /*override fun onStart() {
        super.onStart()

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Maybe you could add Rage Comics here?", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

    }*/


    /**
     * Stops the Activity
     * - unregister listeners
     * - release allocated resources
     */
    /*override fun onStop() {
        super.onStop()

        //fab.setOnClickListener(null)
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, RagecomicListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }*/
}
