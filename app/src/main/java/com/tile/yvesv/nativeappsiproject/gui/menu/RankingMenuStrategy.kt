package com.tile.yvesv.nativeappsiproject.gui.menu

import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.activities.BoredActivity
import com.tile.yvesv.nativeappsiproject.gui.activities.InfoActivity
import com.tile.yvesv.nativeappsiproject.gui.activities.PlayersActivity
import com.tile.yvesv.nativeappsiproject.gui.activities.RankingActivity

class RankingMenuStrategy : MenuStrategy
{
    override val activityDescription: String = "RankingActivity"

    override fun menuSetup(act: AppCompatActivity, item: MenuItem) : Boolean
    {
        var tapped = false
        when (item.itemId)
        {
            R.id.players ->
            {
                val intent = PlayersActivity.newIntent(act.applicationContext)
                act.startActivity(intent)

                Logger.i("Players selected in $activityDescription")
                tapped = true
            }
            R.id.info ->
            {
                val intent = InfoActivity.newIntent(act.applicationContext)
                act.startActivity(intent)

                Logger.i("Info selected in $activityDescription")
                tapped = true
            }
            R.id.nogames ->
            {
                val intent = BoredActivity.newIntent(act.applicationContext)
                act.startActivity(intent)

                Logger.i("No games selected in $activityDescription")
                tapped = true
            }
            //else -> return super.onOptionsItemSelected(item)
        }
        return tapped
    }
}