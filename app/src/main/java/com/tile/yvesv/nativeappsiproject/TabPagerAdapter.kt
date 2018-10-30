package com.tile.yvesv.nativeappsiproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment?
    {
        return when (position)
        {
            0 -> RankingFragment.newInstance()
            1 -> RankingFragment.newInstance()
            else ->
            {
                InfoFragment.newInstance()
            }
        }
    }

    override fun getCount(): Int
    {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence
    {
        return when (position)
        {
            0 -> "Ranking"
            1 -> "Players"
            2 -> "Info"
            else ->
            {
                return ""
            }
        }
    }
}