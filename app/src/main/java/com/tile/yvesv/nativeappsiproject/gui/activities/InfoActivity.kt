package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.orhanobut.logger.Logger
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.menu.InfoMenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuStrategy
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity(), MenuInterface
{
    override val menuStrategy: MenuStrategy = InfoMenuStrategy()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        /*btn_email.setOnClickListener {
            this.openEmail()
        }*/
    }

    override fun onResume()
    {
        super.onResume()
        btn_email.setOnClickListener{this.openEmail()}
    }

    override fun onPause()
    {
        super.onPause()
        btn_email.setOnClickListener(null)
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

                Toast.makeText(this, "Ranking selected", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.players ->
            {
                val intent = PlayersActivity.newIntent(this.applicationContext)

                startActivity(intent)

                Logger.i("Players selected")
                return true
            }
            R.id.nogames -> {
                val intent = BoredActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Logger.i("No games to play? selected")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }*/
    }

    /**
     * Create a new implicit intent for opening a new e-mail message
     */
    private fun openEmail()
    {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "yves.vanduynslager@telenet.be", null))

        //Sets the subject for the e-mail
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "DartsApp: ")

        //check if there is an e-mail app present or not
        checkForCompatibility(emailIntent)
    }

    /**
     * Checks whether there is an application available to handle the intent. If not, it will
     * start the Google Play market place to see whether an application exists which can be installed.
     * If there is an app available, it will start the Activity of that app.
     */
    private fun checkForCompatibility(intent: Intent, requestCode: Int? = null)
    {
        //Check whether an activity exists to start with the provided intent
        val manager = this.packageManager
        val name = intent.resolveActivity(manager)

        if (name == null)
        {
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.packageName))
            if (marketIntent.resolveActivity(manager) != null)
            {
                startActivity(marketIntent)
            }
            else
            {
                val emailToast = Toast.makeText(this, "Could not find the market activity", Toast.LENGTH_LONG)
                emailToast.show()
            }
        }
        else
        {
            if (requestCode == null)
                startActivity(intent)
            else
                startActivityForResult(intent, requestCode)
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
            return Intent(context, InfoActivity::class.java)
        }
    }
}
