package com.tile.yvesv.nativeappsiproject.gui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tile.yvesv.nativeappsiproject.R

class InfoFragment : Fragment()
{
    companion object
    {
        fun newInstance(): InfoFragment
        {
            return InfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }
}
