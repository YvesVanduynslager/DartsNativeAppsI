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
import com.tile.yvesv.nativeappsiproject.model.PlayerViewModelScoreModifier
import kotlinx.android.synthetic.main.fragment_player_details.*
import kotlinx.android.synthetic.main.fragment_player_details.view.*
import java.io.Serializable

class PlayerDetailsFragment : Fragment(), View.OnClickListener
{
    private lateinit var player: Player
    private lateinit var playerViewModel: PlayerViewModel

    private var activityFragmentListener: DetailFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        /*PlayerViewModel needs to be initialized using ViewModelProviders for auto updating on state changes
        PlayerViewModel uses MutableLiveData for properties*/
        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)

        arguments!!.let {
            if (it.containsKey(PLAYER))
            {
                //assign the selected player to player variable
                this.player = it.getSerializable(PLAYER) as Player

                //this.playerViewModel.score.value = this.player.playerData.score
                this.playerViewModel.name.value = this.player.name
                this.playerViewModel.description.value = this.player.description
                this.playerViewModel.score.value = this.player.score
            }
        }
    }

    override fun onResume()
    {
        super.onResume()

        /*activity will be RankingActivity or PlayerDetailActivity depending on
        the device (cellphone or tablet)*/
        activityFragmentListener = activity as DetailFragmentListener

        plus_one.setOnClickListener(this)
        minus_one.setOnClickListener(this)
        btn_save.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }

    override fun onPause()
    {
        super.onPause()
        activityFragmentListener = null
        plus_one.setOnClickListener(null)
        minus_one.setOnClickListener(null)
        btn_save.setOnClickListener(null)
        btn_cancel.setOnClickListener(null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        //DON'T EVER FORGET THIS LINE FOR DATABINDING FFS
        val binding: FragmentPlayerDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_details, container, false)
        val rootView = binding.root

        player.let {
            //rootView.txt_name.text = it.playerData.name
            //score is handled with databinding
            //rootView.txt_score.text = "${playerViewModel.score.value}"

            //rootView.txt_description.text = it.playerData.description
            //rootView.img_player.setImageResource(it.playerData.imageResId)
        }

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

        when (view?.id)
        {
            btn_save.id ->
            {
                //player.playerData.score = Integer.parseInt(txt_score.text.toString())
                this.savePlayerScore()
                this.activityFragmentListener!!.notifyChange(player, playerViewModel)
            }
            btn_cancel.id ->
            {
                this.resetPlayerScore()
            }
            plus_one.id ->
            {
                scoreModifier.increaseScoreByOne()
            }
            minus_one.id ->
            {
                try
                {
                    scoreModifier.decreaseScoreByOne()
                }
                catch (e: ZeroException) //catch exception on attempt to decrease score below 0
                {
                    //log the error message
                    Log.e("Exception", e.message)
                    //display a toast notifying the user that he can't decrease below 0
                    showToast("Score can not be lower than 0!")
                }
            }

        }
    }

    private fun savePlayerScore()
    {
        player.playerData.score = Integer.parseInt(txt_score.text.toString())
        showToast("Saved score")
    }

    private fun resetPlayerScore()
    {
        playerViewModel.score.value = player.playerData.score
        showToast("Reset score")
    }

    private fun showToast(text: String)
    {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    interface DetailFragmentListener
    {
        fun notifyChange(player: IPlayer, vm: PlayerViewModel)
    }
}