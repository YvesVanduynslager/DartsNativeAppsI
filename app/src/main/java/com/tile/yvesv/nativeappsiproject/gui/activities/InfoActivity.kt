package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.menu.InfoMenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuInterface
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuStrategy
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity(), MenuInterface
{
    override val menuStrategy: MenuStrategy = InfoMenuStrategy()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
    }

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
         * Create new intent for InfoActivity
         */
        fun newIntent(context: Context): Intent
        {
            return Intent(context, InfoActivity::class.java)
        }
    }
}
