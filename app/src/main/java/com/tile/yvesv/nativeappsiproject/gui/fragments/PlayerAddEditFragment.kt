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
import com.tile.yvesv.nativeappsiproject.databinding.FragmentPlayerAddEditBinding
import com.tile.yvesv.nativeappsiproject.gui.CRUD_operation
import com.tile.yvesv.nativeappsiproject.gui.fragments.PlayerDetailsFragment.Companion.PLAYER
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.PlayerViewModel
import com.tile.yvesv.nativeappsiproject.model.IPlayer
import com.tile.yvesv.nativeappsiproject.model.Player
import kotlinx.android.synthetic.main.fragment_player_add_edit.*
import java.io.Serializable

class PlayerAddEditFragment : Fragment(), View.OnClickListener
{
    private lateinit var player: Player
    private lateinit var crud: CRUD_operation
    private var twoPane: Boolean = false

    private lateinit var playerViewModel: PlayerViewModel

    private var activityFragmentListener: AddEditFragmentListener? = null

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

                this.playerViewModel.name.value = this.player.name
                this.playerViewModel.description.value = this.player.description
                this.playerViewModel.score.value = this.player.score
            }
            if (it.containsKey(CRUD))
            {
                this.crud = it.getSerializable(CRUD) as CRUD_operation
            }
            if (it.containsKey(TWOPANE))
            {
                this.twoPane = it.getSerializable(TWOPANE) as Boolean
            }
        }
    }

    /**
     * Register listeners when this fragment enters resume state
     */
    override fun onResume()
    {
        super.onResume()
        Log.i("AddEdit", "Fragment resumed")

        /*activity will be RankingActivity or PlayerDetailActivity depending on
        the width (cellphone or tablet)*/
        activityFragmentListener = activity as AddEditFragmentListener
        Log.i("AddEdit", "Registered callback")

        btn_save.setOnClickListener(this)
        btn_delete.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        Log.i("AddEdit", "Registered click listeners")
    }

    /**
     * Unregister listeners when this fragment enters pause state
     */
    override fun onPause()
    {
        super.onPause()
        Log.i("AddEdit", "Fragment paused")

        activityFragmentListener = null
        Log.i("AddEdit", "Unregistered callback")

        btn_save.setOnClickListener(null)
        btn_delete.setOnClickListener(null)
        btn_cancel.setOnClickListener(null)
        Log.i("AddEdit", "Unregistered click listeners")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        //DON'T EVER FORGET THIS LINE FOR DATABINDING FFS
        val binding: FragmentPlayerAddEditBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_add_edit, container, false)
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

    override fun onClick(view: View?)
    {
        when (view?.id)
        {
            btn_save.id ->
            {
                this.savePlayer()
                this.back()
            }
            btn_cancel.id ->
            {
                this.back()
            }
            btn_delete.id ->
            {
                this.deletePlayer()
                this.back()
            }
        }
    }

    private fun back()
    {
        if (!twoPane)
        {
            activity!!.onBackPressed()
        }
    }

    private fun savePlayer()
    {
        when (crud)
        {
            CRUD_operation.CREATE ->
            {
                player.name = txt_name.text.toString()
                player.score = 0
                player.description = txt_description.text.toString()

                activityFragmentListener!!.create(player)
                showToast("Created player ${player.name}")
            }
            CRUD_operation.UPDATE ->
            {
                player.name = txt_name.text.toString()
                player.description = txt_description.text.toString()

                activityFragmentListener!!.update(player)
                showToast("Updated player ${player.name}")
            }
            else ->
            {
                Log.e("CRUD_operation", "No operation passed")
            }
        }
    }

    private fun deletePlayer()
    {
        activityFragmentListener!!.delete(player)
        showToast("Deleted player ${player.name}")
    }

    private fun showToast(text: String)
    {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    interface AddEditFragmentListener
    {
        fun create(player: IPlayer)
        fun update(player: IPlayer)
        fun delete(player: IPlayer)
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
        const val CRUD = "crud"
        const val TWOPANE = "twopane"

        /**
         * The fragment needs to know if we're working in twoPane mode to handle
         * back navigation in a correct way. See [back] method
         */
        fun newInstance(player: IPlayer, crud: CRUD_operation, twoPane: Boolean): PlayerAddEditFragment
        {
            val args = Bundle()

            args.putSerializable(CRUD, crud as Serializable)
            args.putSerializable(PLAYER, player as Serializable)
            args.putSerializable(TWOPANE, twoPane as Serializable)

            val fragment = PlayerAddEditFragment()
            fragment.arguments = args

            return fragment
        }

        //To be used for creating a new player instead of pasing CRUD_operation value
        fun newInstance(): PlayerAddEditFragment
        {
            val args = Bundle()

            val fragment = PlayerAddEditFragment()
            fragment.arguments = args

            return fragment
        }
    }
}