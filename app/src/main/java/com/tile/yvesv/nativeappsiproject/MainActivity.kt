package com.tile.yvesv.nativeappsiproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
}