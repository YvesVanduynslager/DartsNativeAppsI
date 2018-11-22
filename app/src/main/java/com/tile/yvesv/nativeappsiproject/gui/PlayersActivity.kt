package com.tile.yvesv.nativeappsiproject.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R

import kotlinx.android.synthetic.main.activity_players.*

class PlayersActivity : AppCompatActivity()
{

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
        //PLACE IN COMPANION OBJECT
        /*
        check which activity invoked, the invoking activity shouldn't be redrawn. (=> 2x stacked)
         */
        when (item.itemId)
        {
            R.id.ranking ->
            {
                val intent = RankingActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Toast.makeText(this, "Ranking selected", Toast.LENGTH_SHORT).show()
                return true
            }
            /*R.id.players ->
            {
                val intent = PlayersActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Toast.makeText(this, "Players selected", Toast.LENGTH_SHORT).show()
                return true
            }*/
            R.id.info ->
            {
                val intent = InfoActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Toast.makeText(this, "Info selected", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object
    {
        fun newIntent(context: Context): Intent
        {
            return Intent(context, PlayersActivity::class.java)

        }
    }

}
