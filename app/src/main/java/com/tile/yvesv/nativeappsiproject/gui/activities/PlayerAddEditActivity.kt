package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.CRUDoperation
import com.tile.yvesv.nativeappsiproject.gui.fragments.PlayerAddEditFragment
import com.tile.yvesv.nativeappsiproject.model.IPlayer
import com.tile.yvesv.nativeappsiproject.model.Player
import com.tile.yvesv.nativeappsiproject.persistence.DartsPlayerViewModel

/**
 * @class [PlayerAddEditActivity]: Activity for adding, editing, deleting a player.
 *
 * @property dartsPlayerViewModel: The ViewModel that is used to CRUD data from the database.
 *
 * @author Yves Vanduynslager
 */
class PlayerAddEditActivity : AppCompatActivity(), PlayerAddEditFragment.AddEditFragmentListener
{
    private lateinit var dartsPlayerViewModel: DartsPlayerViewModel

    /** Creates the Activity.
     * On newIntent call the onCreate method is called
     * and we retrieve the serialized data added by [PlayersActivity] */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_add_edit)

        /**[dartsPlayerViewModel] needs to be initialized using ViewModelProviders
         * because of the use of MutableLiveData */
        dartsPlayerViewModel = ViewModelProvider(this).get(DartsPlayerViewModel::class.java)

        /** [savedInstanceState] is non-null when there is fragment state saved from previous
         * configurations of this activity (e.g. when rotating the screen from portrait to landscape).
         * Or the activity has been killed due to system constraints.
         * In this case, the fragment will automatically be re-added to its container so we don't need to manually add it.
         * http://developer.android.com/guide/components/fragments.html */
        if (savedInstanceState == null)
        {
            /* Create the detail fragment and add it to the activity
            using a fragment transaction. */
            val addEditFragment = PlayerAddEditFragment.newInstance(
                    intent.getSerializableExtra(PlayerAddEditFragment.PLAYER) as IPlayer,
                    /** The fragment needs to know which CRUD operation took place */
                    intent.getSerializableExtra(PlayerAddEditFragment.CRUD) as CRUDoperation,
                    /** The fragment needs to know if it's operating in dual pane mode or not
                     * for back navigation. So we retrieve the serialized TWOPANE data passed by
                     * [PlayersActivity] and pass this data to a new instance
                     * of [PlayerAddEditFragment] */
                    intent.getSerializableExtra(PlayerAddEditFragment.TWOPANE) as Boolean
            )

            /* Add the fragment to a container in the activity */
            supportFragmentManager.beginTransaction()
                    .add(R.id.player_detail_container, addEditFragment)
                    .commit()
            //TODO: change name of player_detail_container for activity_player_add_edit.xml
        }
    }

    /**
     * Callback for [PlayerAddEditFragment]
     * Calls [dartsPlayerViewModel] to insert a new Player into the database.
     * @param player: player passed from [PlayerAddEditFragment]
     */
    override fun create(player: IPlayer)
    {
        Log.i("CRUDoperation", "Inserting $player")
        dartsPlayerViewModel.insert(player as Player)
    }

    /**
     * Callback for [PlayerAddEditFragment]
     * Calls [dartsPlayerViewModel] to update an existing player in the database.
     * @param player: player passed from [PlayerAddEditFragment]
     */
    override fun update(player: IPlayer)
    {
        Log.i("CRUDoperation", "Updating $player")
        dartsPlayerViewModel.update(player as Player)
    }

    /**
     * Callback for [PlayerAddEditFragment]
     * Calls [dartsPlayerViewModel] to remove a player from the database.
     * @param player: player passed from [PlayerAddEditFragment]
     */
    override fun delete(player: IPlayer)
    {
        Log.i("CRUDoperation", "Deleting $player")
        dartsPlayerViewModel.delete(player as Player)
    }

    companion object
    {
        /**
         * Creates a new intent for [PlayerAddEditActivity]
         * @param context: The context in which this intent needs to be created
         * @return new intent for [PlayerAddEditActivity]
         */
        fun newIntent(context: Context): Intent
        {
            return Intent(context, PlayerAddEditActivity::class.java)
        }
    }
}
