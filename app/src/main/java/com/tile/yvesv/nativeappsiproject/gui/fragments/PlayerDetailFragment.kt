package com.tile.yvesv.nativeappsiproject.gui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.databinding.FragmentPlayerDetailsBinding
import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.PlayerViewModel
import com.tile.yvesv.nativeappsiproject.model.IPlayer
import com.tile.yvesv.nativeappsiproject.model.Player
import com.tile.yvesv.nativeappsiproject.model.ScoreModifier
import kotlinx.android.synthetic.main.fragment_player_details.*
import java.io.Serializable

/**
 * @class [PlayerDetailFragment]: Fragment for viewing a player's details and editing a player's score.
 *
 * @property player: player that needs to be added, edited or deleted.
 * @property playerViewModel: ViewModel that holds the (temporary) changes made to the player.
 * @property isDualPane: Boolean to later determine if the app is running in dual pane or not. Default is false.
 * @property activityFragmentListener: Listener to callback to the fragments parent Activity.
 *
 * @author Yves Vanduynslager
 */
class PlayerDetailFragment : Fragment(), View.OnClickListener
{
    private lateinit var player: Player
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var scoreModifier: ScoreModifier


    private var isDualPane: Boolean = false
    private var activityFragmentListener: DetailFragmentListener? = null

    /**
     * Fundamental setup for the fragment, such as declaring the user interface (defined in an XML layout file),
     * defining member variables, and configuring some of the UI
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        /**[playerViewModel] needs to be initialized using ViewModelProviders
         * because of the use of MutableLiveData */
        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)
        /**
         * setup the scoreModifier with [playerViewModel]
         */
        scoreModifier = ScoreModifier(playerViewModel)

        /* Retrieve the serialized data */
        arguments!!.let {
            if (it.containsKey(PLAYER))
            {
                //assign the serialized player to player variable
                this.player = it.getSerializable(PLAYER) as Player

                //save the player data in the playerViewModel
                this.playerViewModel.name.value = this.player.name
                this.playerViewModel.description.value = this.player.description
                this.playerViewModel.score.value = this.player.score
            }
            if(it.containsKey(TWOPANE))
            {
                this.isDualPane = it.getSerializable(TWOPANE) as Boolean
            }
        }
    }

    /**
     * Register listeners when this fragment enters resume state
     */
    override fun onResume()
    {
        super.onResume()
        Log.i("Details", "Fragment resumed")

        /*activity will be RankingActivity or PlayerDetailActivity depending on
        the width (cellphone or tablet)*/
        activityFragmentListener = activity as DetailFragmentListener
        Log.i("Details", "Registered callback")

        plus_one.setOnClickListener(this)
        minus_one.setOnClickListener(this)
        btn_save.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        Log.i("Details", "Registered click listeners")
    }

    /**
     * Unregister listeners when this fragment enters pause state
     */
    override fun onPause()
    {
        super.onPause()
        Log.i("Details", "Fragment paused")

        activityFragmentListener = null
        Log.i("Details", "Unregistered callback")

        plus_one.setOnClickListener(null)
        minus_one.setOnClickListener(null)
        btn_save.setOnClickListener(null)
        btn_cancel.setOnClickListener(null)
        Log.i("Details", "Unregistered click listeners")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        //Configure the data binding
        val binding: FragmentPlayerDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_details, container, false)
        val rootView = binding.root

        binding.playerViewModel = playerViewModel

        /*Setting the LifeCycleOwner to this fragment is required, as otherwise the UI won't update when changes occur.
        This is because LiveData only updates when the owner is in an ACTIVE state. (fragment = active)
        If there' no owner, it can't be active.
        We need the binding to set up the link with the variables defined in the layout file. Setting the
        LifeCycleOwner is needed when using LiveData. Without this step the LiveData objects can’t
        know when their listeners are active or not.*/
        binding.setLifecycleOwner(this)

        return rootView
    }

    /**
     * Programmatically press the back button.
     * If we're working in dualPane mode, back press must not happen.
     */
    private fun back()
    {
        if (!isDualPane)
        {
            activity!!.onBackPressed()
        }
    }

    /**
     * [View.OnClickListener] override
     * @param view: the view that fired the event
     */
    override fun onClick(view: View?)
    {
        //val scoreModifier = ScoreModifier(playerViewModel)

        //Check which view fired the event
        when (view?.id)
        {
            //Save button tapped
            btn_save.id ->
            {
                this.savePlayer()
                this.back()
            }
            //Cancel button tapped
            btn_cancel.id ->
            {
                this.resetScore()
            }
            //+1 button tapped
            plus_one.id ->
            {
                scoreModifier.increaseScoreByOne()
            }
            //-1 button tapped
            minus_one.id ->
            {
                try
                {
                    scoreModifier.decreaseScoreByOne()
                }
                //Catch exception on attempt to decrease score below 0
                catch (e: ZeroException)
                {
                    //log the error message
                    Log.e("Exception", e.message)
                    //display a toast notifying the user that he can't decrease below 0
                    showToast("Score can not be lower than 0!")
                }
            }

        }
    }

    /**
     * Callback the parent Activity to save the updated [player]
     */
    private fun savePlayer()
    {
        player.saveScore(playerViewModel.score.value!!)
        //player.score = playerViewModel.score.value!!
        showToast("Saved score for player ${player.name}")
        activityFragmentListener!!.update(player)
    }

    /**
     * Reset the player's score to it's original unsaved value
     */
    private fun resetScore()
    {
        scoreModifier.resetScore(player.score)
        //playerViewModel.resetScore(player.score)
        //playerViewModel.score.value = player.score
        showToast("Reset score")
    }

    /**
     * Creates and shows a Toast
     * @param text: The text to be displayed in the Toast
     */
    private fun showToast(text: String)
    {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    /**
     * Callback methods to be implemented by parent activity
     */
    interface DetailFragmentListener
    {
        fun update(player: IPlayer)
    }

    companion object
    {
        const val PLAYER = "player"
        const val TWOPANE = "twopane"

        /**
         * The fragment needs to know if we're working in dualPane mode to handle
         * back navigation in a correct way. See [back] method
         *
         * @param player: The player we want to edit or add.
         * @param isDualPane: Boolean to determine if the app is running in dual pane or not.
         */
        fun newInstance(player: IPlayer, isDualPane: Boolean): PlayerDetailFragment
        {
            val args = Bundle()

            //Serialize the passed values
            args.putSerializable(PLAYER, player as Serializable)
            args.putSerializable(TWOPANE, isDualPane as Serializable)

            //Create the fragment and add the serialized data
            val fragment = PlayerDetailFragment()
            fragment.arguments = args

            return fragment
        }
    }
}