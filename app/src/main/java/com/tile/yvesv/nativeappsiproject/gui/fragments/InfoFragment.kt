package com.tile.yvesv.nativeappsiproject.gui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.tile.yvesv.nativeappsiproject.R
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment()
{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
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
        val manager = activity!!.packageManager
        val name = intent.resolveActivity(manager)

        if (name == null)
        {
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity!!.packageName))
            if (marketIntent.resolveActivity(manager) != null)
            {
                startActivity(marketIntent)
            }
            else
            {
                val emailToast = Toast.makeText(activity, "Could not find the market activity", Toast.LENGTH_LONG)
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
}
