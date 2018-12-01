package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.orhanobut.logger.Logger
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.BoredActivityViewModel
import kotlinx.android.synthetic.main.activity_bored.*

class BoredActivity : AppCompatActivity()
{

    private var act: BoredActivityViewModel = BoredActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bored)
        btn_bored.setOnClickListener { this.newBoredActivity() }

    }

    override fun onStart()
    {
        super.onStart()
        newBoredActivity()
    }

    /*private fun isConnected(): Boolean
    {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }*/

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
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
                //Toast.makeText(this, "Players selected", Toast.LENGTH_SHORT).show()
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
            else -> return super.onOptionsItemSelected(item)
        }
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
