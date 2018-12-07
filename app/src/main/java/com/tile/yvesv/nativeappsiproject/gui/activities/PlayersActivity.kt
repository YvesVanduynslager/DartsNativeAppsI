package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuInterface
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.menu.PlayersMenuStrategy

import kotlinx.android.synthetic.main.activity_players.*

class PlayersActivity : AppCompatActivity(), MenuInterface
{
    override val menuStrategy: MenuStrategy = PlayersMenuStrategy()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

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
                //Toast.makeText(this, "Ranking selected", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.info ->
            {
                val intent = InfoActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Logger.i("Info selected")
                //Toast.makeText(this, "Info selected", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.nogames ->
            {
                val intent = BoredActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Logger.i("No games to play? selected")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }*/
    }

    companion object
    {
        fun newIntent(context: Context): Intent
        {
            return Intent(context, PlayersActivity::class.java)

        }
    }

}
