package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.R.id.txt_activity
import com.tile.yvesv.nativeappsiproject.gui.menu.BoredMenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuInterface
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.BoredActivityViewModel
import kotlinx.android.synthetic.main.activity_bored.*

/**
 * @class [BoredActivity]: Displays an activity (not to be confused with Android activities)
 * to perform when there are no games to play. In this use case this is only used to demonstrate networking functionality.
 *
 * @property menuStrategy: The top menu implementation that is used for this activity.
 * @property act: The ViewModel that is used to get data from the API.
 *
 * @author Yves Vanduynslager
 */
class BoredActivity : AppCompatActivity(), MenuInterface, View.OnClickListener
{
    override val menuStrategy: MenuStrategy = BoredMenuStrategy()
    private var act: BoredActivityViewModel = BoredActivityViewModel()

    /**
     * Fundamental setup for the activity, such as declaring the user interface (defined in an XML layout file),
     * defining member variables, and configuring some of the UI
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bored)

        /**[BoredActivityViewModel] needs to be initialized using ViewModelProviders
         * because of the use of MutableLiveData */
        act = ViewModelProvider(this).get(BoredActivityViewModel::class.java)
    }

    /**
     * Register listeners when [BoredActivity] enters resume state
     */
    override fun onResume()
    {
        super.onResume()
        Log.i("boredAPI", "Activity resumed")

        btn_bored.setOnClickListener(this)
        Log.i("boredAPI", "Registered click listener")
    }

    /**
     * Unregister listeners when [BoredActivity] enters pause state
     */
    override fun onPause()
    {
        super.onPause()
        Log.i("boredAPI", "Activity paused")

        btn_bored.setOnClickListener(null)
        Log.i("boredAPI", "Unregistered click listener")
    }

    /**
     * Displays the menu
     * @return [Boolean]
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     * Handles the menu selection
     * @param item The selected menu-item
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return menuStrategy.menuSetup(this, item)
    }

    /**
     * [View.OnClickListener] override
     * @param view: the view that fired the event
     */
    override fun onClick(view: View?)
    {
        //check which view fired the event
        when (view?.id)
        {
            btn_bored.id ->
            {
                /** Request a new activity and display it in [txt_activity] */
                act.newActivity()
                Log.i("boredAPI", "Requested new activity")

                txt_activity.text = act.getRawAct().value
                Log.i("boredAPI", "Received: ${act.getRawAct().value}")
            }
        }
    }

    companion object
    {
        /*
         * Id's for the activities which return results
         */
        //private const val PICK_EMAIL = 1

        /**
         * Creates a new intent for [BoredActivity]
         * @param context: The context in which this intent needs to be created
         * @return new intent for [BoredActivity]
         */
        fun newIntent(context: Context): Intent
        {
            return Intent(context, BoredActivity::class.java)
        }
    }
}
