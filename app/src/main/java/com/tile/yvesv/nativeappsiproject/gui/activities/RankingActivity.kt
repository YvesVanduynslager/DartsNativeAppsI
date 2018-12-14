package com.tile.yvesv.nativeappsiproject.gui.activities

//import com.tile.yvesv.nativeappsiproject.model.PlayerData
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
import com.tile.yvesv.nativeappsiproject.gui.fragments.PlayerDetailsFragment
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
 * Activity with 3 tabs that can switch between 3 fragments
 */
class RankingActivity : AppCompatActivity(), PlayerDetailsFragment.DetailFragmentListener, MenuInterface
{
    override val menuStrategy: MenuStrategy = RankingMenuStrategy()

    private lateinit var dartsPlayerViewModel: DartsPlayerViewModel
    private lateinit var playersListAdapter: SimpleItemRecyclerViewAdapter


    override fun notifyChange(player: IPlayer)
    {
        dartsPlayerViewModel.update(player as Player)
        print(player)
        //playersListAdapter.notifyDataSetChanged()
    }

    private var isDualPane: Boolean = false
    //private var players: List<Player>? = null
    /**
     * Fundamental setup for the activity, such as declaring the user interface (defined in an XML layout file),
     * defining member variables, and configuring some of the UI
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        if (player_detail_container != null)
        {
            /**
             * [player_detail_container] sits in player_rank_list.xml(w900dp)
             * the player_rank_list file is selected based on the width of the screen
             * if the player_rank_list.xml file with 900dp width is selected.
             * there will be a [player_detail_container] view present
             * if that is not null we set isDualPane to true
             * this boolean is then used in the [playersListAdapter]. See below
             */
            isDualPane = true
        }

        dartsPlayerViewModel = ViewModelProviders.of(this).get(DartsPlayerViewModel::class.java)
    }

    /**
     * Makes the activity visible to the user, as the app prepares for the activity to enter the foreground and become interactive
     */
    override fun onStart()
    {
        super.onStart()
        //players = this.getPlayers()

        dartsPlayerViewModel.allPlayers.observe(this, Observer { players ->
            players?.let { playersListAdapter.setPlayers(it) }
        })

        playersListAdapter = SimpleItemRecyclerViewAdapter(this, isDualPane)
        player_list.adapter = playersListAdapter

        //player_list.adapter = SimpleItemRecyclerViewAdapter(this, players!!, isDualPane)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return menuStrategy.menuSetup(this, item)
    }

    class SimpleItemRecyclerViewAdapter(private val parentActivity: RankingActivity,
            /*private val players: List<Player>,*/
                                        private val twoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>()
    {

        private var playersCached = emptyList<Player>()

        private val onClickListener: View.OnClickListener

        init
        {
            onClickListener = View.OnClickListener { view ->
                // Every view has a tag that can be used to store data related to that view
                // Here each item in the RecyclerView keeps a reference to the player it represents.
                // This allows us to reuse a single listener for all items in the list
                val item = view.tag as Player
                //Log.d("CLICKED PLAYER", item.playerData.name)

                Log.d("CLICKED PLAYER", item.name)
                Log.d("TWO_PANE", twoPane.toString())

                if (twoPane)
                {
                    val fragment = PlayerDetailsFragment.newInstance(item)
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.player_detail_container, fragment)
                            .commit()
                }
                else
                {
                    //EXPLICIT INTENT
                    val intent = PlayerDetailActivity.newIntent(view.context).apply {
                        putExtra(PlayerDetailsFragment.PLAYER, item)
                    }

                    view.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.player_rank_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int)
        {
            //val player = players[position]
            val player = playersCached[position]

            //holder.name.text = player.playerData.name
            //holder.score.text = "${player.playerData.score}"
            holder.name.text = player.name
            holder.score.text = "${player.score}"
            holder.rank.text = "${(position + 1)}"
            //holder.image.setImageResource(player.playerData.imageResId)

            with(holder.itemView) {
                tag = player // Save the player represented by this view
                setOnClickListener(onClickListener)
            }
        }

        internal fun setPlayers(players: List<Player>)
        {
            this.playersCached = PlayerSorter.sortOnScoreDesc(players)
            notifyDataSetChanged()
        }

        //override fun getItemCount() = players.size
        override fun getItemCount() = playersCached.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
        {
            val name: TextView = view.txt_name
            val score: TextView = view.txt_score
            val rank: TextView = view.txt_rank
        }
    }

    companion object
    {
        fun newIntent(context: Context): Intent
        {
            return Intent(context, RankingActivity::class.java)
        }
    }
}