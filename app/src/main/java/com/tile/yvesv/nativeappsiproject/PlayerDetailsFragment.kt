/**
 * Copyright (c) 2017 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tile.yvesv.nativeappsiproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tile.yvesv.nativeappsiproject.databinding.FragmentPlayerDetailsBinding
import com.tile.yvesv.nativeappsiproject.domain.Player
import com.tile.yvesv.nativeappsiproject.domain.PlayerScoreModifier
import kotlinx.android.synthetic.main.fragment_player_details.*
import java.io.Serializable

//1
class PlayerDetailsFragment : Fragment(), View.OnClickListener
{
    /**
     * Since we want to dynamically populate the UI of the PlayerDetailsFragment with the selection,
     * we grab the reference to the FragmentRageComicDetailsBinding in the fragment view in onCreateView.
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

        fun newInstance(player: Player): PlayerDetailsFragment
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
            //player.increaseScoreByOne()

        }
        else
        {
            try
            {
                scoreModifier.decreaseScoreByOne()
            }
            catch (e: Exception)
            {
                Log.e("Exception", e.message)
                TODO("display error message in view")
            }
            //player.decreaseScoreByOne()
        }

        txt_score.text = "${player.playerData.score}"
    }
}
