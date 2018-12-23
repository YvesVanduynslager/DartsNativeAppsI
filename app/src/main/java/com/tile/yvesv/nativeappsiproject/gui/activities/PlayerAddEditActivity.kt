package com.tile.yvesv.nativeappsiproject.gui.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.CRUD
import com.tile.yvesv.nativeappsiproject.gui.fragments.PlayerAddEditFragment
import com.tile.yvesv.nativeappsiproject.model.IPlayer
import com.tile.yvesv.nativeappsiproject.model.Player
import com.tile.yvesv.nativeappsiproject.persistence.DartsPlayerViewModel

class PlayerAddEditActivity : AppCompatActivity(), PlayerAddEditFragment.AddEditFragmentListener
{
    private lateinit var dartsPlayerViewModel: DartsPlayerViewModel
    /**
     * Creates the Activity
     * on newIntent call the onCreate method is called.
     * in this method we
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_add_edit)

        dartsPlayerViewModel = ViewModelProviders.of(this).get(DartsPlayerViewModel::class.java)
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

            val addEditFragment = PlayerAddEditFragment.newInstance(
                    intent.getSerializableExtra(PlayerAddEditFragment.PLAYER) as IPlayer,
                    intent.getSerializableExtra(PlayerAddEditFragment.CRUD) as CRUD
            )

            supportFragmentManager.beginTransaction()
                    .add(R.id.add_edit_container, addEditFragment)
                    .commit()
        }
    }

    /*override fun addEdit(player: IPlayer, crud: CRUD)
    {
        when (crud)
        {
            CRUD.CREATE -> dartsPlayerViewModel.insert(player as Player)
            CRUD.UPDATE -> dartsPlayerViewModel.update(player as Player)
            CRUD.DELETE -> dartsPlayerViewModel.delete(player as Player)
            else ->
            {
                Log.e("CRUD", "No CRUD type passed")
            }
        }
        //dartsPlayerViewModel.insert(player as Player)
        //dartsPlayerViewModel.update(player as Player)
        print(player)
    }*/

    override fun create(player: IPlayer)
    {
        Log.e("CRUD", "Inserting $player")
        dartsPlayerViewModel.insert(player as Player)
    }
    override fun update(player: IPlayer)
    {
        Log.e("CRUD", "Updating $player")
        dartsPlayerViewModel.update(player as Player)
    }
    override fun delete(player: IPlayer)
    {
        Log.e("CRUD", "Deleting $player")
        dartsPlayerViewModel.delete(player as Player)
    }

    companion object
    {
        fun newIntent(context: Context): Intent
        {
            return Intent(context, PlayerAddEditActivity::class.java)
        }
    }
}
