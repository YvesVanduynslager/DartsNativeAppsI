package com.tile.yvesv.nativeappsiproject.gui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.domain.IPlayer
import com.tile.yvesv.nativeappsiproject.domain.Player
import com.tile.yvesv.nativeappsiproject.domain.PlayerScoreModifier
import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException
import kotlinx.android.synthetic.main.fragment_player_details.*
import kotlinx.android.synthetic.main.fragment_player_details.view.*
import java.io.Serializable

class PlayerDetailsFragment : Fragment(), View.OnClickListener
{
    private lateinit var player: Player
    private var activityFragmentListener: DetailFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        arguments!!.let {
            if(it.containsKey(PLAYER))
            {
                this.player = it.getSerializable(PLAYER) as Player
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
        //val view = FragmentPlayerDetailsBinding.inflate(inflater, container, false)
        val rootView = inflater.inflate(R.layout.fragment_player_details, container, false)

        rootView.plus_one.setOnClickListener(this)
        rootView.minus_one.setOnClickListener(this)

        player.let {
            rootView.txt_name.text = it.playerData.name
            rootView.txt_score.text = "${it.playerData.score}"
            rootView.txt_description.text = it.playerData.description
            rootView.img_player.setImageResource(it.playerData.imageResId)
        }

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
        val scoreModifier = PlayerScoreModifier(player)

        // +1 button tapped
        if (view?.id == plus_one.id)
        {
            scoreModifier.increaseScoreByOne()
            this.activityFragmentListener!!.notifyChange(player)
        }
        else // -1 button tapped
        {
            try
            {
                scoreModifier.decreaseScoreByOne()
                this.activityFragmentListener!!.notifyChange(player)
            }
            catch (e: ZeroException) //catch exception on attempt to decrease score below 0
            {
                //log the error message
                Log.e("Exception", e.message)
                //display a toast notifying the user that he can't decrease below 0
                Toast.makeText(activity, getString(R.string.less_than_zero_error), Toast.LENGTH_LONG).show()
            }
        }

        txt_score.text = "${player.playerData.score}"
    }

    interface DetailFragmentListener
    {
        fun notifyChange(player: IPlayer)
    }
}