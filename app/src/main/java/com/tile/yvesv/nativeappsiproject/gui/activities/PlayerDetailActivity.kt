package com.tile.yvesv.nativeappsiproject.gui.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.fragments.PlayerDetailFragment
import com.tile.yvesv.nativeappsiproject.model.IPlayer
import com.tile.yvesv.nativeappsiproject.model.Player
import com.tile.yvesv.nativeappsiproject.persistence.DartsPlayerViewModel

/**
 * @class [PlayerDetailActivity]: Activity for viewing a player's details and editing a player's score.
 *
 * @property dartsPlayerViewModel: The ViewModel that is used to CRUD data from the database.
 *
 * @author Yves Vanduynslager
 * */
class PlayerDetailActivity : AppCompatActivity(), PlayerDetailFragment.DetailFragmentListener
{
    private lateinit var dartsPlayerViewModel: DartsPlayerViewModel

    /** Creates the Activity.
     * On newIntent call the onCreate method is called
     * and we retrieve the serialized data added by PlayersActivity */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        /**[dartsPlayerViewModel] needs to be initialized using ViewModelProviders
         * because of the use of MutableLiveData */
        dartsPlayerViewModel = ViewModelProviders.of(this).get(DartsPlayerViewModel::class.java)

        /** [savedInstanceState] is non-null when there is fragment state saved from previous
         * configurations of this activity (e.g. when rotating the screen from portrait to landscape).
         * In this case, the fragment will automatically be re-added to its container so we don't need to manually add it.
         * http://developer.android.com/guide/components/fragments.html */
        if (savedInstanceState == null)
        {
            /* Create the detail fragment and add it to the activity
            using a fragment transaction. */
            val detailFragment = PlayerDetailFragment.newInstance(
                    intent.getSerializableExtra(PlayerDetailFragment.PLAYER) as IPlayer,
                    /** The fragment needs to know if it's operating in dual pane mode or not
                     * for back navigation. So we retrieve the serialized TWOPANE data passed by
                     * [RankingActivity] and pass this data to a new instance
                     * of [PlayerDetailFragment] */
                    intent.getSerializableExtra(PlayerDetailFragment.TWOPANE) as Boolean
            )

            /* Add the fragment to a container in the activity */
            supportFragmentManager.beginTransaction()
                    .add(R.id.player_detail_container, detailFragment)
                    .commit()
            //TODO: change name of player_detail_container for activity_player_add_edit.xml
        }
    }

    /**
     * Callback for [PlayerDetailFragment]
     * Calls [dartsPlayerViewModel] to update an existing player in the database.
     * @param player: player passed from [PlayerDetailFragment]
     */
    override fun update(player: IPlayer)
    {
        Log.i("CRUDoperation", "Updating $player")
        dartsPlayerViewModel.update(player as Player)
    }

    companion object
    {
        /**
         * Creates a new intent for [PlayerDetailActivity]
         * @param context: The context in which this intent needs to be created
         * @return new intent for [PlayerDetailActivity]
         */
        fun newIntent(context: Context): Intent
        {
            return Intent(context, PlayerDetailActivity::class.java)
        }
    }
}
