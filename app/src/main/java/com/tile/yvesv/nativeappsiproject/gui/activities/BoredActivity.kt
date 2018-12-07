package com.tile.yvesv.nativeappsiproject.gui.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.menu.BoredMenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuInterface
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.BoredActivityViewModel
import kotlinx.android.synthetic.main.activity_bored.*

/**
 * @class [BoredActivity]
 * This activity displays a random activity to perform when there are no games to play
 * Not to be confused with Android activities ;)
 * Used only to demonstrate networking functionality.
 */
class BoredActivity : AppCompatActivity(), MenuInterface
{
    override val menuStrategy: MenuStrategy = BoredMenuStrategy()
    private var act: BoredActivityViewModel = BoredActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bored)

        /**[BoredActivityViewModel] needs to be initialized using ViewModelProviders
        * because of the use of MutableLiveData */
        act = ViewModelProviders.of(this).get(BoredActivityViewModel::class.java)
        btn_bored.setOnClickListener { this.newBoredActivity() }
    }

    /**
     * Request a new activity and display it in [txt_activity]
     */
    private fun newBoredActivity()
    {
        act.newActivity()
        txt_activity.text = act.getRawAct().value
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
        /*when (item.itemId)
        {
            R.id.ranking ->
            {
                val intent = RankingActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Logger.i("Ranking selected")
                return true
            }
            R.id.players ->
            {
                val intent = PlayersActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Logger.i("Players selected")
                return true
            }
            R.id.info ->
            {
                val intent = InfoActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Logger.i("Info selected")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }*/
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
            return Intent(context, BoredActivity::class.java)
        }
    }
}
