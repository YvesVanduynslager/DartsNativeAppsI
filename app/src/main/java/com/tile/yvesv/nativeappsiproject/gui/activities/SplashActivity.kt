package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.tile.yvesv.nativeappsiproject.R

/**
 * @class [SplashActivity]
 * Splash screen activity. Shows title then loads RankingActivity after 3 seconds
 */
class SplashActivity : AppCompatActivity()
{
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 200 //FAST FOR DEVELOPMENT

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing)
        {
            val intent = Intent(applicationContext, RankingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy()
    {

        if (mDelayHandler != null)
        {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}