package com.tile.yvesv.nativeappsiproject

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

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