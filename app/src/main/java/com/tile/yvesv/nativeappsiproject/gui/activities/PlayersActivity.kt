package com.tile.yvesv.nativeappsiproject.gui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.TextView
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.R.id.player_detail_container
import com.tile.yvesv.nativeappsiproject.gui.CRUDoperation
import com.tile.yvesv.nativeappsiproject.gui.fragments.PlayerAddEditFragment
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuInterface
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.menu.PlayersMenuStrategy
import com.tile.yvesv.nativeappsiproject.model.IPlayer
import com.tile.yvesv.nativeappsiproject.model.Player
import com.tile.yvesv.nativeappsiproject.model.PlayerSorter
import com.tile.yvesv.nativeappsiproject.persistence.DartsPlayerViewModel
import kotlinx.android.synthetic.main.activity_players.*
import kotlinx.android.synthetic.main.player_rank_item.view.*
import kotlinx.android.synthetic.main.player_rank_list.*

/**
 * @class [PlayersActivity]: Activity for displaying the list of players alphabetically.
 *
 * @property menuStrategy: The top menu implementation that is used for this activity.
 * @property dartsPlayerViewModel: The ViewModel that is used to CRUD data from the database.
 * @property rankListAdapter: The RecyclerView adapter to display player data in the table.
 * @property isDualPane: Boolean to later determine if the app is running in dual pane or not. Default is false.
 *
 * @author Yves Vanduynslager
 * */
class PlayersActivity : AppCompatActivity(), MenuInterface, PlayerAddEditFragment.AddEditFragmentListener, View.OnClickListener
{
    override val menuStrategy: MenuStrategy = PlayersMenuStrategy()
    private lateinit var dartsPlayerViewModel: DartsPlayerViewModel
    private lateinit var rankListAdapter: PlayersActivity.SimpleItemRecyclerViewAdapter
    private var isDualPane: Boolean = false

    /**
     * Fundamental setup for the activity, such as declaring the user interface (defined in an XML layout file),
     * defining member variables, and configuring some of the UI
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        /**
         * If [player_detail_container] has no value, we are working in single pane mode
         * player_rank_list.xml will be selected based on the width of the device.
         * player_rank_list.xml (w900dp) has a player_detail_container.
         * player_rank_list.xml does not
         */
        if (player_detail_container != null)
        {
            isDualPane = true
        }

        /**[dartsPlayerViewModel] needs to be initialized using ViewModelProviders
         * because of the use of MutableLiveData */
        dartsPlayerViewModel = ViewModelProviders.of(this).get(DartsPlayerViewModel::class.java)
    }

    /**
     * Makes the activity visible to the user, as the app prepares for the activity to enter the foreground and become interactive
     */
    override fun onStart()
    {
        super.onStart()

        /** Observe the allPlayers list from [dartsPlayerViewModel] */
        dartsPlayerViewModel.allPlayers.observe(this, Observer { players ->
            players?.let { rankListAdapter.setPlayers(it) }
        })

        /**Initialize the recycler view adapter*/
        rankListAdapter = PlayersActivity.SimpleItemRecyclerViewAdapter(this, isDualPane)
        player_list.adapter = rankListAdapter
    }

    /**
     * Register listeners when [PlayersActivity] enters resume state
     */
    override fun onResume()
    {
        super.onResume()
        btn_add.setOnClickListener(this)
    }

    /**
     * Unregister listeners when this [PlayersActivity] enters pause state
     */
    override fun onPause()
    {
        super.onPause()
        btn_add.setOnClickListener(null)
    }

    /**
     * Displays the menu
     * @return [Boolean]
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     * Handles the menu selection
     * @param item The selected menu-item
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return menuStrategy.menuSetup(this, item)
    }

    /**
     * Callback for [PlayerAddEditFragment]
     * Calls [dartsPlayerViewModel] to insert a new Player into the database.
     * @param player: player passed from [PlayerAddEditFragment]
     */
    override fun create(player: IPlayer)
    {
        Log.e("CRUDoperation", "Inserting $player")
        dartsPlayerViewModel.insert(player as Player)
    }

    /**
     * Callback for [PlayerAddEditFragment]
     * Calls [dartsPlayerViewModel] to update an existing player in the database.
     * @param player: player passed from [PlayerAddEditFragment]
     */
    override fun update(player: IPlayer)
    {
        Log.e("CRUDoperation", "Updating $player")
        dartsPlayerViewModel.update(player as Player)
    }

    /**
     * Callback for [PlayerAddEditFragment]
     * Calls [dartsPlayerViewModel] to remove a player from the database.
     * @param player: player passed from [PlayerAddEditFragment]
     */
    override fun delete(player: IPlayer)
    {
        Log.e("CRUDoperation", "Deleting $player")
        dartsPlayerViewModel.delete(player as Player)
    }

    /**
     * [View.OnClickListener] override
     * @param view: the view that fired the event
     */
    override fun onClick(view: View?)
    {
        //Check which view fired the event
        when (view?.id)
        {
            btn_add.id ->
            {
                newPlayer()
            }
        }
    }

    private fun newPlayer()
    {
        if (isDualPane)
        {
            /* If we are working in dualPane mode, we want to start a new fragment
            in player_detail_container located in the layout file for
            our list (player_rank_list.xml) */

            //Pass 'empty' player object to PlayerAddEditFragment (new player)
            //TODO: change implementation of PlayerAddEditFragment.newInstance, so it doesn't need a Player object passed.
            val fragment = PlayerAddEditFragment.newInstance(Player(name = "", description = "", score = 0), CRUDoperation.CREATE, isDualPane)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.player_detail_container, fragment)
                    .commit()
        }
        else
        {
            /* If we are not working in dualPane mode, we want to start a new PlayerAddEditActivity
             PlayerAddEditActivity also has a player_detail_container.
             PlayerAddEditActivity's onCreate() will add the PlayerAddEditFragment to
             player_detail_container of PlayerAddEditActivity */

            //Pass empty player object to PlayerAddEditFragment (new player)
            val intent = PlayerAddEditActivity.newIntent(this.applicationContext).apply {
                putExtra(PlayerAddEditFragment.PLAYER, Player(name = "", description = "", score = 0))
                /* The fragment needs to know which CRUD operation took place */
                putExtra(PlayerAddEditFragment.CRUD, CRUDoperation.CREATE)
                /* The fragment needs to know if it's operating in dual pane mode or not
                 * for back navigation */
                putExtra(PlayerAddEditFragment.TWOPANE, isDualPane)
            }

            startActivity(intent)
        }
    }

    /**
     * RecyclerView setup
     * @param parentActivity: The activity that holds the recyclerview
     * @param isDualPane: Is the app running in dualPane mode or not?
     */
    class SimpleItemRecyclerViewAdapter(private val parentActivity: PlayersActivity, private val isDualPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>()
    {
        private var playersCached = emptyList<Player>()
        private val onClickListener: View.OnClickListener

        init
        {
            onClickListener = View.OnClickListener { view ->
                /* Every view has a tag that can be used to store data related to that view
                Here each item in the RecyclerView keeps a reference to the player it represents.
                This allows us to reuse a single listener for all items in the list */
                val item = view.tag as Player

                if (isDualPane)
                {
                    val fragment = PlayerAddEditFragment.newInstance(item, CRUDoperation.UPDATE, isDualPane)
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.player_detail_container, fragment)
                            .commit()
                }
                else
                {
                    val intent = PlayerAddEditActivity.newIntent(view.context).apply {
                        putExtra(PlayerAddEditFragment.PLAYER, item)
                        putExtra(PlayerAddEditFragment.CRUD, CRUDoperation.UPDATE)
                        putExtra(PlayerAddEditFragment.TWOPANE, isDualPane)
                    }

                    view.context.startActivity(intent)
                }
            }
        }

        /**
         * Sets the layout for individual list items
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.player_rank_item, parent, false)

            return ViewHolder(view)
        }

        /**
         * Binds the data from a player to one list item
         * And adds a click listener
         */
        override fun onBindViewHolder(holder: ViewHolder, position: Int)
        {
            val player = playersCached[position]

            holder.name.text = player.name

            with(holder.itemView) {
                tag = player // Save the player represented by this view
                setOnClickListener(onClickListener)
            }
        }

        /**
         * Sets the player list to be used in the recycler view
         * @param players: List with player data
         */
        internal fun setPlayers(players: List<Player>)
        {
            this.playersCached = PlayerSorter.sortOnNameAsc(players)
            notifyDataSetChanged()
        }

        override fun getItemCount() = playersCached.size

        /**
         * Sets the properties to be used in the ViewHolder
         */
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
        {
            val name: TextView = view.txt_name
        }
    }

    companion object
    {
        /**
         * Creates a new intent for [PlayersActivity]
         * @param context: The context in which this intent needs to be created
         * @return new intent for [PlayersActivity]
         */
        fun newIntent(context: Context): Intent
        {
            return Intent(context, PlayersActivity::class.java)
        }
    }
}
