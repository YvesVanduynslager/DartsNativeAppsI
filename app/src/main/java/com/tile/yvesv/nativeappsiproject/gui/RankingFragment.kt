package com.tile.yvesv.nativeappsiproject.gui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.databinding.RecyclerItemPlayerBinding
import com.tile.yvesv.nativeappsiproject.domain.IPlayer
import com.tile.yvesv.nativeappsiproject.domain.Player
import com.tile.yvesv.nativeappsiproject.domain.PlayerData

class RankingFragment : Fragment()
{
    private var players: List<IPlayer>? = null

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
     * Accesses the resources you need via the Context to which the fragment is attached. (MainActivity)
     *
     * This method will be called first, even before onCreate(),
     * letting us know that your fragment has been attached to an activity.
     * You are passed the Activity that will host your fragment
     */
    override fun onAttach(context: Context?)
    {
        super.onAttach(context)

        //This initializes the listener reference
        if (context is OnPlayerSelected)
        {
            //MainActivity implements OnPlayerSelected, so MainActivity is OnPlayerSelected
            listener = context
        }
        else
        {
            throw ClassCastException(context.toString() + "  must implement OnPlayerSelected")
        }
    }


    /**
     * The system calls this callback when it’s time for the fragment to draw its UI for the first time.
     * To draw a UI for the fragment, a View component must be returned from this method which is the root of the fragment’s layout.
     * We can return null if the fragment does not provide a UI
     */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        /**
         * Retreive playes from resources
         */
        players = getPlayers()

        /**
         * setup view with fragment_ranking, add to container (= parent view)
         */
        val view: View = inflater.inflate(R.layout.fragment_ranking, container, false)

        /**
         * fill recyclerview with players
         */
        val recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 1) //int hier aantal items per rij
        recyclerView.adapter = PlayerAdapter(activity!!, players!!)

        return view
    }

    /**
     * The onStart() method is called once the fragment gets visible
     */
    override fun onStart()
    {
        super.onStart()
    }

    /**
     * Placeholder code until database logic is implemented
     */
    private fun getPlayers(): List<Player>
    {
        val playerList = mutableListOf<Player>()

        val resources = context!!.resources

        val names = resources.getStringArray(R.array.names)
        val descriptions = resources.getStringArray(R.array.extras)
        val scores = resources.getIntArray(R.array.scores)

        /**
         * Get images
         */
        val typedArray = resources.obtainTypedArray(R.array.images)
        val imageResIds = IntArray(names.size)

        /**
         * Fill list with players
         */
        for (i in 0 until names.size)
        {
            imageResIds[i] = typedArray.getResourceId(i, 0)
            val playerData = PlayerData(imageResIds[i], names[i], descriptions[i], scores[i])
            val thePlayer = Player(playerData)
            playerList.add(thePlayer)
        }
        typedArray.recycle()

        /**
         * Sort the players descending on their score and return
         */
        return playerList.sortedWith(compareByDescending {
            it.playerData.score
        })
    }


    /**
     * RECYCLERVIEW
     */
    internal inner class PlayerAdapter(context: Context, private val players: List<IPlayer>) : RecyclerView.Adapter<ViewHolder>()
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
            val player = players[position]
            viewHolder.setData(player)

            /**
             * add click listener for each item (player)
             * invokes the callback on the listener (the activity) to pass along the selection
             */
            viewHolder.itemView.setOnClickListener {
                listener.onPlayerSelected(player)
            }
        }

        override fun getItemCount(): Int
        {
            return players.size
        }
    }

    internal inner class ViewHolder constructor(itemView: View, private val recyclerItemPlayerBinding: RecyclerItemPlayerBinding) : RecyclerView.ViewHolder(itemView)
    {
        /**
         * sets an item in the recyclerview
         */
        fun setData(player: IPlayer)
        {
            /**
             * databinding for the recyclerview happens here
             */
            recyclerItemPlayerBinding.player = player as Player
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
        fun onPlayerSelected(player: IPlayer)
    }
}