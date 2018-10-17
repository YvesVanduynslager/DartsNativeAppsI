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
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tile.yvesv.nativeappsiproject.databinding.RecyclerItemRageComicBinding
import com.tile.yvesv.nativeappsiproject.domain.Comic

class RageComicListFragment : Fragment()
{
    private lateinit var imageResIds: IntArray
    private lateinit var names: Array<String>
    private lateinit var descriptions: Array<String>
    private lateinit var urls: Array<String>

    //reference to the fragment’s listener, which is the activity.
    private lateinit var listener : OnRageComicSelected

    companion object
    {
        fun newInstance(): RageComicListFragment {
            return RageComicListFragment()
        }
    }

    /**
     * Accesses the resources you need via the Context to which the fragment is attached.
     */
    override fun onAttach(context: Context?)
    {
        super.onAttach(context)

        //This initializes the listener reference
        if(context is OnRageComicSelected)
        {
            listener = context
        }
        else
        {
            throw ClassCastException(context.toString() + "  must implement OnRageComicSelected")
        }

        // Get rage face names and descriptions.
        //val resources = context!!.resources
        val resources = context.resources
        names = resources.getStringArray(R.array.names)
        descriptions = resources.getStringArray(R.array.descriptions)
        urls = resources.getStringArray(R.array.urls)

        // Get rage face images.
        val typedArray = resources.obtainTypedArray(R.array.images)
        val imageCount = names.size
        imageResIds = IntArray(imageCount)
        for (i in 0 until imageCount)
        {
            imageResIds[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view: View = inflater.inflate(R.layout.fragment_rage_comic_list, container,false)
        val activity = activity
        val recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = RageComicAdapter(activity!!)
        return view
    }

    /**
     *  Whenever a view has a data field, the framework automatically generates a binding object.
     *  The name of the object is derived by converting the snake case name of the view into camel case and adding binding to the name.
     *  For example, a view called recycler_item_rage_comic.xml would have a corresponding binding called RecyclerItemRageComicBinding.
     *
     *  You can then inflate the view via the inflater method on the binding object and set properties via standard property access mechanisms.
     *  Data binding follows a Model-View-ViewModel (MVVM) pattern. MVVM consists of three components:
     *
     *  A View: The layout file.
     *  A Model: The data class
     *  A View Model/Binder: The auto-generated binding files.
     */
    internal inner class RageComicAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>()
    {
        private val layoutInflater: LayoutInflater

        init
        {
            layoutInflater = LayoutInflater.from(context)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
        {
            val recyclerItemRageComicBinding = RecyclerItemRageComicBinding.inflate(layoutInflater,
                    viewGroup, false)
            return ViewHolder(recyclerItemRageComicBinding.root, recyclerItemRageComicBinding)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
        {
            val comic = Comic(imageResIds[position], names[position],
                    descriptions[position], urls[position])
            viewHolder.setData(comic)

            //add click listener to each item (comic)
            viewHolder.itemView.setOnClickListener {
                //invokes the callback on the listener (the activity) to pass along the selection.
                listener.onRageComicSelected(comic)
            }
        }

        override fun getItemCount(): Int
        {
            return names.size
        }
    }


    internal inner class ViewHolder constructor(itemView: View, val recyclerItemRageComicBinding: RecyclerItemRageComicBinding) :
            RecyclerView.ViewHolder(itemView)
    {

        fun setData(comic: Comic)
        {
            //databinding: data hier binden
            recyclerItemRageComicBinding.comic = comic
        }
    }

    /**
     * This defines a listener interface for the activity to listen to the fragment.
     * The activity will implement this interface, and the fragment will invoke the onRageComicSelected()
     * when an item is selected, passing the selection to the activity.
     */
    interface OnRageComicSelected
    {
        fun onRageComicSelected(comic: Comic)
    }
}

