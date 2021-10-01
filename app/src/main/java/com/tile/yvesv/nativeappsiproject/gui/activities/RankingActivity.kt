package com.tile.yvesv.nativeappsiproject.gui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.fragments.PlayerDetailFragment
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuInterface
import com.tile.yvesv.nativeappsiproject.gui.menu.MenuStrategy
import com.tile.yvesv.nativeappsiproject.gui.menu.RankingMenuStrategy
import com.tile.yvesv.nativeappsiproject.model.IPlayer
import com.tile.yvesv.nativeappsiproject.model.Player
import com.tile.yvesv.nativeappsiproject.model.PlayerSorter
import com.tile.yvesv.nativeappsiproject.persistence.DartsPlayerViewModel
import kotlinx.android.synthetic.main.player_rank_item.view.*
import kotlinx.android.synthetic.main.player_rank_list.*

/**
 * @class [RankingActivity]: Activity for displaying a sorted list of players based on their score (high to low).
 *
 * @property menuStrategy: The top menu implementation that is used for this activity.
 * @property dartsPlayerViewModel: The ViewModel that is used to CRUD data from the database.
 * @property rankListAdapter: The RecyclerView adapter to display player data in the table.
 * @property isDualPane: Boolean to later determine if the app is running in dual pane or not. Default is false.
 *
 * @author Yves Vanduynslager
 * */
class RankingActivity : AppCompatActivity(), PlayerDetailFragment.DetailFragmentListener, MenuInterface
{
    override val menuStrategy: MenuStrategy = RankingMenuStrategy()
    private lateinit var dartsPlayerViewModel: DartsPlayerViewModel
    private lateinit var rankListAdapter: RankingActivity.SimpleItemRecyclerViewAdapter
    private var isDualPane: Boolean = false

    /**
     * Fundamental setup for the activity, such as declaring the user interface (defined in an XML layout file),
     * defining member variables, and configuring some of the UI
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

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
        dartsPlayerViewModel = ViewModelProvider(this).get(DartsPlayerViewModel::class.java)
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

        /**Initialize the recycler view adapter */
        rankListAdapter = SimpleItemRecyclerViewAdapter(this, isDualPane)
        player_list.adapter = rankListAdapter

        /** Set the layoutManager for the recyclerView (ex. GridLayoutManager, StaggeredGridLayoutManager */
        player_list.layoutManager = LinearLayoutManager(this.applicationContext)
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
     * Callback for [PlayerDetailFragment]
     * Calls [dartsPlayerViewModel] to update an existing player in the database.
     * @param player: player passed from [PlayerDetailFragment]
     */
    override fun update(player: IPlayer)
    {
        dartsPlayerViewModel.update(player as Player)
    }

    /**
     * RecyclerView setup
     * @param parentActivity: The activity that holds the recyclerview
     * @param isDualPane: Is the app running in dualPane mode or not?
     */
    class SimpleItemRecyclerViewAdapter(private val parentActivity: RankingActivity, private val isDualPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>()
    {
        private var playersCached = emptyList<Player>()
        private val onClickListener: View.OnClickListener

        init
        {
            onClickListener = View.OnClickListener { view ->
                /* Every view has a tag that can be used to store data related to that view
                Here each item in the RecyclerView keeps a reference to the player it represents.
                This allows us to reuse a single listener for all items in the list. */
                val item = view.tag as Player

                if (isDualPane)
                {
                    val fragment = PlayerDetailFragment.newInstance(item, isDualPane)
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.player_detail_container, fragment)
                            .commit()
                }
                else
                {
                    val intent = PlayerDetailActivity.newIntent(view.context).apply {
                        putExtra(PlayerDetailFragment.PLAYER, item)
                        putExtra(PlayerDetailFragment.TWOPANE, isDualPane)
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
            val view = LayoutInflater.from(parent.context).inflate(R.layout.player_rank_item, parent, false)
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
            holder.score.text = "${player.score}"
            holder.rank.text = "${(position + 1)}"

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
            this.playersCached = PlayerSorter.sortOnScoreDesc(players)
            notifyDataSetChanged()
        }

        override fun getItemCount() = playersCached.size

        /**
         * Sets the properties to be used in the ViewHolder
         */
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
        {
            val name: TextView = view.txt_name
            val score: TextView = view.txt_score
            val rank: TextView = view.txt_rank
        }
    }

    companion object
    {
        /**
         * Creates a new intent for [RankingActivity]
         * @param context: The context in which this intent needs to be created
         * @return new intent for [RankingActivity]
         */
        fun newIntent(context: Context): Intent
        {
            return Intent(context, RankingActivity::class.java)
        }
    }
}