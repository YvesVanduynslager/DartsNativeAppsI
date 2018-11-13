package com.tile.yvesv.nativeappsiproject.gui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.tile.yvesv.nativeappsiproject.R

/**
 * Splash screen activity. Shows title then loads MainActivity after 3 seconds
 */
class SplashActivity : AppCompatActivity()
{
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 200 //FAST FOR DEVELOPMENT

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing)
        {

            val intent = Intent(applicationContext, MainActivity::class.java)
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