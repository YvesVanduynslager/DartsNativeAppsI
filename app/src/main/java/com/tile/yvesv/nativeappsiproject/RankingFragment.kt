package com.tile.yvesv.nativeappsiproject

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tile.yvesv.nativeappsiproject.databinding.RecyclerItemPlayerBinding
import com.tile.yvesv.nativeappsiproject.domain.Player
import com.tile.yvesv.nativeappsiproject.domain.PlayerData

class RankingFragment : Fragment()
{
    private lateinit var imageResIds: IntArray
    private lateinit var names: Array<String>
    private lateinit var extras: Array<String>
    private lateinit var scores: IntArray

    //reference to the fragment’s listener, which is the activity.
    private lateinit var listener: OnPlayerSelected

    companion object
    {
        fun newInstance(): RankingFragment
        {
            return RankingFragment()
        }
    }

    /**
     * Accesses the resources you need via the Context to which the fragment is attached.
     */
    override fun onAttach(context: Context?)
    {
        super.onAttach(context)

        //This initializes the listener reference
        if (context is OnPlayerSelected)
        {
            listener = context
        }
        else
        {
            throw ClassCastException(context.toString() + "  must implement OnPlayerSelected")
        }

        // Get player attributes
        //val resources = context!!.resources
        val resources = context.resources
        names = resources.getStringArray(R.array.names)
        extras = resources.getStringArray(R.array.extras)
        scores = resources.getIntArray(R.array.scores)

        // Get player images and scores.
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
        //setup view with fragment_ranking, add to container (= parent view)
        val view: View = inflater.inflate(R.layout.fragment_ranking, container, false)

        val activity = activity
        val recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 1) //int hier aantal items per rij
        recyclerView.adapter = PlayerAdapter(activity!!)

        return view
    }

    /**
     *  Whenever a view has a data field, the framework automatically generates a binding object.
     *  The name of the object is derived by converting the snake case name of the view into camel case and adding binding to the name.
     *  For example, a view called recycler_item_player.xmlld have a corresponding binding called RecyclerItemRageComicBinding.
     *
     *  You can then inflate the view via the inflater method on the binding object and set properties via standard property access mechanisms.
     *  Data binding follows a Model-View-ViewModel (MVVM) pattern. MVVM consists of three components:
     *
     *  A View: The layout file.
     *  A Model: The data class
     *  A View Model/Binder: The auto-generated binding files.
     */
    internal inner class PlayerAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>()
    {
        private val layoutInflater: LayoutInflater

        init
        {
            layoutInflater = LayoutInflater.from(context)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
        {
            val recyclerItemPlayerBinding = RecyclerItemPlayerBinding.inflate(layoutInflater,
                    viewGroup, false)
            return ViewHolder(recyclerItemPlayerBinding.root, recyclerItemPlayerBinding)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
        {
            val playerData = PlayerData(imageResIds[position], names[position], extras[position], scores[position])
            val player = Player(playerData)
            viewHolder.setData(player)

            //add click listener to each item (player)
            viewHolder.itemView.setOnClickListener {
                //invokes the callback on the listener (the activity) to pass along the selection.
                listener.onPlayerSelected(player)
            }
        }

        override fun getItemCount(): Int
        {
            return names.size
        }
    }


    internal inner class ViewHolder constructor(itemView: View, val recyclerItemPlayerBinding: RecyclerItemPlayerBinding) : RecyclerView.ViewHolder(itemView)
    {

        fun setData(player: Player)
        {
            //databinding: data hier binden
            recyclerItemPlayerBinding.player = player
        }
    }

    /**
     * Callback interface
     *
     * This defines a listener interface for the activity to listen to the fragment.
     * The activity will implement this interface, and the fragment will invoke the onPlayerSelected()
     * when an item is selected, passing the selection to the activity.
     */
    interface OnPlayerSelected
    {
        fun onPlayerSelected(player: Player)
    }
}