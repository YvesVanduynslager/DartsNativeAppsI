/**
 * Copyright (c) 2017 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tile.yvesv.nativeappsiproject

import com.tile.yvesv.nativeappsiproject.databinding.FragmentRageComicDetailsBinding
import java.io.Serializable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tile.yvesv.nativeappsiproject.domain.Comic

//1
class RageComicDetailsFragment : Fragment()
{
    /**
     * Since we want to dynamically populate the UI of the RageComicDetailsFragment with the selection,
     * we grab the reference to the FragmentRageComicDetailsBinding in the fragment view in onCreateView.
     * Next, we bind the view comic with the Comic that we’ve passed to RageComicDetailsFragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val fragmentRageComicDetailsBinding = FragmentRageComicDetailsBinding.inflate(inflater, container, false)

        val comic = arguments!!.getSerializable(COMIC) as Comic
        fragmentRageComicDetailsBinding.comic = comic
        comic.text = String.format(getString(R.string.description_format), comic.description, comic.url)
        return fragmentRageComicDetailsBinding.root
    }

    /**
     * A fragment can take initialization parameters through its arguments, which you access via the arguments property.
     * The arguments are actually a Bundle that stores them as key-value pairs, just like the Bundle in Activity.onSaveInstanceState.
     * You create and populate the arguments’ Bundle, set the arguments, and when you need the values later, you reference arguments property to retrieve them.
     * As you learned earlier, when a fragment is re-created, the default empty constructor is used — no parameters for you.
     * Because the fragment can recall initial parameters from its persisted arguments, you can utilize them in the re-creation.
     * The code below also stores information about the selected Rage Comic in the RageComicDetailsFragment arguments.
     */
    companion object
    {
        private const val COMIC = "comic"

        fun newInstance(comic: Comic): RageComicDetailsFragment {
            val args = Bundle()
            args.putSerializable(COMIC, comic as Serializable)
            val fragment = RageComicDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
