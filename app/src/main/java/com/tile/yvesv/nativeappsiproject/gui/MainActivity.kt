package com.tile.yvesv.nativeappsiproject.gui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.domain.Player
import com.tile.yvesv.nativeappsiproject.domain.PlayerData
import com.tile.yvesv.nativeappsiproject.domain.PlayerSorter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_item.view.*
import kotlinx.android.synthetic.main.player_list.*

/**
 * Activity with 3 tabs that can switch between 3 fragments
 */
class MainActivity : AppCompatActivity()
{
    private var twoPane: Boolean = false
    private var players: List<Player>? = null
    /**
     * Fundamental setup for the activity, such as declaring the user interface (defined in an XML layout file),
     * defining member variables, and configuring some of the UI
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (player_detail_container != null)
        {
            /*
            * player_detail_container sits in player_list(w900dp)
            * the player_list file is selected based on the width of the screen
            * if the player_list.xml file with 900dp width is selected.
            * there will be a player_detail_container view present
            * if that is not null we set twoPane to true
            * this boolean is then used in the recyclerviewadapter. See below
             */
            twoPane = true
        }
    }

    /**
     * Makes the activity visible to the user, as the app prepares for the activity to enter the foreground and become interactive
     */
    override fun onStart()
    {
        super.onStart()
        players = this.getPlayers()
        player_list.adapter = SimpleItemRecyclerViewAdapter(this, players!!, twoPane)

        /**
         * setup for tabbed layout
         */
        /*val fragmentAdapter = TabPagerAdapter(supportFragmentManager, this.applicationContext, 1)
        viewpager_main.adapter = fragmentAdapter
        tab_layout_main.setupWithViewPager(viewpager_main)*/
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        //PLACE IN COMPANION OBJECT
        /*
        check which activity invoked, the invoking activity shouldn't be redrawn. (=> 2x stacked)
         */
        when (item.itemId)
        {
            /*R.id.ranking ->
            {
                val intent = MainActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Toast.makeText(this, "Ranking selected", Toast.LENGTH_SHORT).show()
                return true
            }*/
            R.id.players ->
            {
                val intent = PlayersActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Toast.makeText(this, "Players selected", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.info ->
            {
                val intent = InfoActivity.newIntent(this.applicationContext)
                startActivity(intent)

                Toast.makeText(this, "Info selected", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    /**
     * Placeholder code until database logic is implemented
     */
    private fun getPlayers(): List<Player>
    {
        val playerList = mutableListOf<Player>()

        val resources = resources

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
        return PlayerSorter.SortOnScoresDesc(playerList)
        /*return playerList.sortedWith(compareByDescending {
                it.playerData.score
        })*/
    }

    /**
     * TabPagerAdapter handles switching between tabs.
     * context is used here to access strings.xml
     * nrOfTabs to set the number of tabs to display.
     */
    /*class TabPagerAdapter(fm: FragmentManager, private val context: Context, private val nrOfTabs: Int) : FragmentPagerAdapter(fm)
    {
        override fun getItem(position: Int): Fragment?
        {
            return when (position)
            {
                0 -> InfoFragment.newInstance()
                else -> InfoFragment.newInstance()
            }
        }

        override fun getCount(): Int
        {
            return nrOfTabs
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return when (position)
            {
                0 -> context.getString(R.string.info)
                else -> context.getString(R.string.info)
            }
        }
    }*/

    class SimpleItemRecyclerViewAdapter(private val parentActivity: MainActivity,
                                        private val players: List<Player>,
                                        private val twoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>()
    {

        private val onClickListener: View.OnClickListener

        init
        {
            onClickListener = View.OnClickListener { view ->
                // Every view has a tag that can be used to store data related to that view
                // Here each item in the RecyclerView keeps a reference to the player it represents.
                // This allows us to reuse a single listener for all items in the list
                val item = view.tag as Player
                Log.d("CLICKED PLAYER", item.playerData.name)

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

                        // val intent = Intent(view.context, PlayerDetailActivity::class.java).apply {
                        //putExtra(PlayerDetailsFragment.PLAYER, item)
                        //IMPLICIT INTENT EXAMPLE:
                        /*button_contacts . setOnClickListener {
                        * val intent = Intent ( Intent. ACTION_PICK ,
                        * ContactsContract.Contacts. CONTENT_URI )
                        * checkForCompatibility (intent , PICK_CONTACT )
                        * }*/
                    }

                    view.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.player_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int)
        {
            val player = players[position]
            holder.name.text = player.playerData.name
            holder.score.text = "${player.playerData.score}"
            //holder.image.setImageResource(player.playerData.imageResId)

            with(holder.itemView) {
                tag = player // Save the player represented by this view
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = players.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
        {
            val name: TextView = view.txt_name
            val score: TextView = view.txt_score
            //val image: ImageView = view.list_comic_image
        }
    }

    companion object
    {
        fun newIntent ( context : Context): Intent
        {
           return Intent ( context , MainActivity::class.java )
        }
    }
}