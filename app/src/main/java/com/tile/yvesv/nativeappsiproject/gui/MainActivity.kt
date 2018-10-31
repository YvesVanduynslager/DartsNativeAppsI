package com.tile.yvesv.nativeappsiproject.gui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.domain.IPlayer
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity with 3 tabs that can switch between 3 fragments
 */
class MainActivity : AppCompatActivity(), RankingFragment.OnPlayerSelected
{
    /**
     * Fundamental setup for the activity, such as declaring the user interface (defined in an XML layout file),
     * defining member variables, and configuring some of the UI
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Makes the activity visible to the user, as the app prepares for the activity to enter the foreground and become interactive
     */
    override fun onStart()
    {
        super.onStart()

        /**
         * setup for tabbed layout
         */
        val fragmentAdapter = TabPagerAdapter(supportFragmentManager, this.applicationContext, 3)
        viewpager_main.adapter = fragmentAdapter
        tab_layout_main.setupWithViewPager(viewpager_main)
    }

    /**
     * Callback from RankingFragment
     * receives Player from RankingFragment and shows details for selected player in a new fragment (PlayerDetailsFragment)
     */
    override fun onPlayerSelected(player: IPlayer)
    {
        val detailsFragment = PlayerDetailsFragment.newInstance(player)
        supportFragmentManager.beginTransaction()
                .replace(R.id.root_layout, detailsFragment, "playerDetails") //add, remove, replace, hide
                .addToBackStack(null)
                .commit()
    }

    /**
     * TabPagerAdapter handles switching between tabs.
     * context is used here to access strings.xml
     * nrOfTabs to set the number of tabs to display.
     */
    class TabPagerAdapter(fm: FragmentManager, private val context: Context, private val nrOfTabs: Int) : FragmentPagerAdapter(fm)
    {
        override fun getItem(position: Int): Fragment?
        {
            return when (position)
            {
                0 -> RankingFragment.newInstance()
                1 -> RankingFragment.newInstance()
                else -> InfoFragment.newInstance()
            }
        }

        override fun getCount(): Int
        {
            return nrOfTabs
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return when (position)
            {
                0 -> context.getString(R.string.ranking)
                1 -> context.getString(R.string.players)
                else -> context.getString(R.string.info)
            }
        }
    }
}