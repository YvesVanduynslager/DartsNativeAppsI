package com.tile.yvesv.nativeappsiproject.gui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R

class InfoActivity : AppCompatActivity()
{

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
                val intent = MainActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Toast.makeText(this, "Ranking selected", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.players ->
            {
                val intent = PlayersActivity.newIntent(this.applicationContext)

                startActivity(intent)

                Toast.makeText(this, "Players selected", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object
    {
        fun newIntent ( context : Context): Intent
        {
            return Intent ( context , InfoActivity::class.java )
        }
    }
}
