package com.tile.yvesv.nativeappsiproject.gui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.databinding.FragmentPlayerDetailsBinding
import com.tile.yvesv.nativeappsiproject.domain.*
import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException
import kotlinx.android.synthetic.main.fragment_player_details.*
import kotlinx.android.synthetic.main.fragment_player_details.view.*
import java.io.Serializable

class PlayerDetailsFragment : Fragment(), View.OnClickListener
{
    private lateinit var player: Player
    private lateinit var playerViewModel : PlayerViewModel

    private var activityFragmentListener: DetailFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //links the viewmodel to the activity's lifecycle
        playerViewModel = ViewModelProviders.of( this ).get( PlayerViewModel::class.java)


        arguments!!.let {
            if(it.containsKey(PLAYER))
            {
                //assign the selected player to player variable
                this.player = it.getSerializable(PLAYER) as Player

                //only the score can be adjusted in this fragment, no need to put the rest in the viewmodel
                //only the score is used with databinding here
                this.playerViewModel.score.value = this.player.playerData.score
            }
        }

    }

    override fun onAttach(context: Context?)
    {
        super.onAttach(context)
        activityFragmentListener = activity as DetailFragmentListener
    }
    override fun onDetach()
    {
        super.onDetach()
        activityFragmentListener = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        //DON'T EVER FORGET THIS LINE FOR DATABINDING FFS
        val binding: FragmentPlayerDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_details, container, false)
        val rootView = binding.root

        rootView.plus_one.setOnClickListener(this)
        rootView.minus_one.setOnClickListener(this)

        player.let {
            rootView.txt_name.text = it.playerData.name
            rootView.txt_score.text = "${playerViewModel.score.value}"
            //score is handled with databinding
            rootView.txt_description.text = it.playerData.description
            rootView.img_player.setImageResource(it.playerData.imageResId)
        }

        binding.playerViewModel = playerViewModel

        //Setting the lifecycleowner to this activity is required, as otherwise the UI won't update when changes occur.
        //This is because LiveData only updates when the owner is in an ACTIVE state.
        //If there's no owner, it can't be active.
        binding.setLifecycleOwner(this)

        return rootView
    }

    /**
     * A fragment can take initialization parameters through its arguments, which you access via the arguments property.
     * The arguments are actually a Bundle that stores them as key-value pairs, just like the Bundle in Activity.onSaveInstanceState.
     * You create and populate the arguments’ Bundle, set the arguments, and when you need the values later, you reference arguments property to retrieve them.
     * As you learned earlier, when a fragment is re-created, the default empty constructor is used — no parameters for you.
     * Because the fragment can recall initial parameters from its persisted arguments, you can utilize them in the re-creation.
     * The code below also stores information about the selected Rage Player in the PlayerDetailsFragment arguments.
     */
    companion object
    {
        const val PLAYER = "player"

        fun newInstance(player: IPlayer): PlayerDetailsFragment
        {
            val args = Bundle()
            args.putSerializable(PLAYER, player as Serializable)
            val fragment = PlayerDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(view: View?)
    {
        val scoreModifier = PlayerViewModelScoreModifier(playerViewModel)
        //val scoreModifier = PlayerScoreModifier(player)

        // +1 button tapped
        if (view?.id == plus_one.id)
        {
            scoreModifier.increaseScoreByOne()
            this.activityFragmentListener!!.notifyChange(player, playerViewModel)
        }
        else // -1 button tapped
        {
            try
            {
                scoreModifier.decreaseScoreByOne()
                this.activityFragmentListener!!.notifyChange(player, playerViewModel)
            }
            catch (e: ZeroException) //catch exception on attempt to decrease score below 0
            {
                //log the error message
                Log.e("Exception", e.message)
                //display a toast notifying the user that he can't decrease below 0
                Toast.makeText(activity, getString(R.string.less_than_zero_error), Toast.LENGTH_LONG).show()
            }
        }

        //No longer needed, score text is now updated through databinding
        //updateUI()//txt_score.text = "${player.playerData.score}"
    }

    private fun savePlayer()
    {
        this.playerViewModel.score.let {
            player.playerData.score = it.value!!
        }
        this.activityFragmentListener!!.notifyChange(player, playerViewModel)
    }

    private fun reset()
    {
        playerViewModel.score.value = player.playerData.score
    }

    //No longer needed, score text is now updated using databinding
    private fun updateUI()
    {
        txt_score.text = "${playerViewModel.score.value}"
    }

    interface DetailFragmentListener
    {
        fun notifyChange(player: IPlayer, vm: PlayerViewModel)
    }
}