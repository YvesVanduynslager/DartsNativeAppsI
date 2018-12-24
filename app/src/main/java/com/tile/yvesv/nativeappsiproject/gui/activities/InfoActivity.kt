package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.menu.InfoMenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuInterface
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuStrategy

/**
 * @class [InfoActivity]: Displays an activity with some info about the creator
 * and an e-mail button which will open an e-mail client if there's one available on the device.
 *
 * @property menuStrategy: The top menu implementation that is used for this activity.
 *
 * @author Yves Vanduynslager
 */
class InfoActivity : AppCompatActivity(), MenuInterface
{
    override val menuStrategy: MenuStrategy = InfoMenuStrategy()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
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

    companion object
    {
        /*
         * Id's for the activities which return results
         */
        //private const val PICK_EMAIL = 1

        /**
         * Creates a new intent for [InfoActivity]
         * @param context: The context in which this intent needs to be created
         * @return new intent for [InfoActivity]
         */
        fun newIntent(context: Context): Intent
        {
            return Intent(context, InfoActivity::class.java)
        }
    }
}
