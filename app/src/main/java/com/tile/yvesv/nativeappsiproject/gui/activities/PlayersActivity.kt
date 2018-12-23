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
import com.tile.yvesv.nativeappsiproject.gui.CRUD_operation
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

class PlayersActivity : AppCompatActivity(), MenuInterface, PlayerAddEditFragment.AddEditFragmentListener
{
    override val menuStrategy: MenuStrategy = PlayersMenuStrategy()

    private lateinit var dartsPlayerViewModel: DartsPlayerViewModel
    private lateinit var rankListAdapter: PlayersActivity.SimpleItemRecyclerViewAdapter

    private var isDualPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        if (player_detail_container != null)
        {
            isDualPane = true
        }

        dartsPlayerViewModel = ViewModelProviders.of(this).get(DartsPlayerViewModel::class.java)

        /*btn_add.setOnClickListener { view ->
            Snackbar.make(view, "Add new player fragment", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
        btn_add.setOnClickListener {
            if (isDualPane)
            {
                //Pass empty player object to PlayerAddEditFragment (new player)
                val fragment = PlayerAddEditFragment.newInstance(Player(name = "", description = "", score = 0), CRUD_operation.CREATE, isDualPane)
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.player_detail_container, fragment)
                        .commit()
            }
            else
            {
                //EXPLICIT INTENT. Pass empty player object to PlayerAddEditFragment (new player)
                val intent = PlayerAddEditActivity.newIntent(this.applicationContext).apply {
                    putExtra(PlayerAddEditFragment.PLAYER, Player(name = "", description = "", score = 0))
                    putExtra(PlayerAddEditFragment.CRUD, CRUD_operation.CREATE)
                }

                startActivity(intent)
            }
        }
    }

    override fun onStart()
    {
        super.onStart()

        dartsPlayerViewModel.allPlayers.observe(this, Observer { players ->
            players?.let { rankListAdapter.setPlayers(it) }
        })

        rankListAdapter = PlayersActivity.SimpleItemRecyclerViewAdapter(this, isDualPane)
        player_list.adapter = rankListAdapter
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

    override fun create(player: IPlayer)
    {
        Log.e("CRUD_operation", "Inserting $player")
        dartsPlayerViewModel.insert(player as Player)
    }

    override fun update(player: IPlayer)
    {
        Log.e("CRUD_operation", "Updating $player")
        dartsPlayerViewModel.update(player as Player)
    }

    override fun delete(player: IPlayer)
    {
        Log.e("CRUD_operation", "Deleting $player")
        dartsPlayerViewModel.delete(player as Player)
    }

    class SimpleItemRecyclerViewAdapter(private val parentActivity: PlayersActivity, private val twoPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>()
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

                if (twoPane)
                {
                    val fragment = PlayerAddEditFragment.newInstance(item, CRUD_operation.UPDATE, twoPane)
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.add_edit_container, fragment)
                            .commit()
                }
                else
                {
                    //EXPLICIT INTENT
                    val intent = PlayerAddEditActivity.newIntent(view.context).apply {
                        putExtra(PlayerAddEditFragment.PLAYER, item)
                        putExtra(PlayerAddEditFragment.CRUD, CRUD_operation.UPDATE)
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
            val player = playersCached[position]

            holder.name.text = player.name

            with(holder.itemView) {
                tag = player // Save the player represented by this view
                setOnClickListener(onClickListener)
            }
        }

        internal fun setPlayers(players: List<Player>)
        {
            this.playersCached = PlayerSorter.sortOnNameAsc(players)
            notifyDataSetChanged()
        }

        override fun getItemCount() = playersCached.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
        {
            val name: TextView = view.txt_name
        }
    }

    companion object
    {
        fun newIntent(context: Context): Intent
        {
            return Intent(context, PlayersActivity::class.java)
        }
    }
}
