package com.tile.yvesv.nativeappsiproject.gui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.databinding.FragmentPlayerDetailsBinding
import com.tile.yvesv.nativeappsiproject.domain.IPlayer
import com.tile.yvesv.nativeappsiproject.domain.Player
import com.tile.yvesv.nativeappsiproject.domain.PlayerScoreModifier
import com.tile.yvesv.nativeappsiproject.exceptions.ZeroException
import kotlinx.android.synthetic.main.fragment_player_details.*
import java.io.Serializable

class PlayerDetailsFragment : Fragment(), View.OnClickListener
{
    /**
     * Since we want to dynamically populate the UI of the PlayerDetailsFragment with the selection,
     * we grab the reference to the FragmentPlayerDetailsBinding in the fragment view in onCreateView.
     * Next, we bind the view comic with the Player that we’ve passed to PlayerDetailsFragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = FragmentPlayerDetailsBinding.inflate(inflater, container, false)

        val player = arguments!!.getSerializable(PLAYER) as Player
        view.player = player
        //player.text = String.format(getString(R.string.description_format), player.description)
        player.playerData.text = player.playerData.description

        view.plusOne.setOnClickListener(this)
        view.minusOne.setOnClickListener(this)

        //player.text = player.description
        return view.root
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
        private const val PLAYER = "player"

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

        val player = arguments!!.getSerializable(PLAYER) as Player
        val scoreModifier = PlayerScoreModifier(player)

        if (view?.id == plus_one.id)
        {
            scoreModifier.increaseScoreByOne()
        }
        else
        {
            try
            {
                scoreModifier.decreaseScoreByOne()
            }
            catch (e: ZeroException)
            {
                Log.e("Exception", e.message)
                Toast.makeText(activity, getString(R.string.less_than_zero_error), Toast.LENGTH_LONG).show()
            }
        }

        txt_score.text = "${player.playerData.score}"
    }
}