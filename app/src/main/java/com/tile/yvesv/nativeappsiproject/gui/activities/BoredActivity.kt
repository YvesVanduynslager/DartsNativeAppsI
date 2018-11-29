package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.orhanobut.logger.Logger
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.BoredActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bored.*

class BoredActivity : AppCompatActivity()
{
    private var act: BoredActivityViewModel = BoredActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bored)
        btn_bored.setOnClickListener{ this.newBoredActivity()}

    }
    override fun onStart()
    {
        super.onStart()
        newBoredActivity()
    }

    private fun newBoredActivity()
    {
        //txt_activity.text = act.getRawAct().value
        var sub = act.boredActApi.getBoredActivity()
                //we tell it to fetch the data on background by
                .subscribeOn(Schedulers.io())
                //we like the fetched data to be displayed on the MainTread (UI)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    txt_activity.text = result.activity
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
        /**
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
