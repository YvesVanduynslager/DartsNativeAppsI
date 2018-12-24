package com.tile.yvesv.nativeappsiproject.gui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.databinding.FragmentPlayerAddEditBinding
import com.tile.yvesv.nativeappsiproject.gui.CRUDoperation
import com.tile.yvesv.nativeappsiproject.gui.viewmodels.PlayerViewModel
import com.tile.yvesv.nativeappsiproject.model.IPlayer
import com.tile.yvesv.nativeappsiproject.model.Player
import kotlinx.android.synthetic.main.fragment_player_add_edit.*
import java.io.Serializable

/**
 * @class [PlayerAddEditFragment]: Fragment for adding, editing, deleting a player.
 *
 * @property player: player that needs to be added, edited or deleted.
 * @property crud: the operation that started this fragment.
 * @property playerViewModel: ViewModel that holds the (temporary) changes made to the player.
 * @property isDualPane: Boolean to later determine if the app is running in dual pane or not. Default is false.
 * @property activityFragmentListener: Listener to callback to the fragments parent Activity.
 *
 * @author Yves Vanduynslager
 */
class PlayerAddEditFragment : Fragment(), View.OnClickListener
{
    private lateinit var player: Player
    private lateinit var crud: CRUDoperation
    private lateinit var playerViewModel: PlayerViewModel

    private var isDualPane: Boolean = false
    private var activityFragmentListener: AddEditFragmentListener? = null

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
            if (it.containsKey(CRUD))
            {
                this.crud = it.getSerializable(CRUD) as CRUDoperation
            }
            if (it.containsKey(TWOPANE))
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
        Log.i("AddEdit", "Fragment resumed")

        /* Activity will be RankingActivity or PlayerDetailActivity depending on
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
        //Configure the data binding
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

    /**
     * [View.OnClickListener] override
     * @param view: the view that fired the event
     */
    override fun onClick(view: View?)
    {
        //Check which view fired the event
        when (view?.id)
        {
            //Save button tapped
            btn_save.id ->
            {
                this.savePlayer()
                view.hideKeyboard()
                this.back()
            }
            //Cancel button tapped
            btn_cancel.id ->
            {
                view.hideKeyboard()
                this.back()
            }
            //Delete button tapped
            btn_delete.id ->
            {
                this.deletePlayer()
                this.back()
            }
        }
    }

    /**
     * Extension method to easily hide the keyboard
     */
    private fun View.hideKeyboard()
    {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
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
     * Callback the parent Activity to save the new or updated [player]
     */
    private fun savePlayer()
    {
        when (crud)
        {
            //New player: CREATE
            CRUDoperation.CREATE ->
            {
                player.name = txt_name.text.toString()
                player.score = 0
                player.description = txt_description.text.toString()

                activityFragmentListener!!.create(player)
                showToast("Created player ${player.name}")
            }
            //Existing player: UPDATE
            CRUDoperation.UPDATE ->
            {
                player.name = txt_name.text.toString()
                player.description = txt_description.text.toString()

                activityFragmentListener!!.update(player)
                showToast("Updated player ${player.name}")
            }
            else ->
            {
                Log.e("CRUDoperation", "No operation passed")
            }
        }
    }

    /**
     * Callback the parent activity to delete [player]
     */
    private fun deletePlayer()
    {
        activityFragmentListener!!.delete(player)
        showToast("Deleted player ${player.name}")
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
    interface AddEditFragmentListener
    {
        fun create(player: IPlayer)
        fun update(player: IPlayer)
        fun delete(player: IPlayer)
    }

    companion object
    {
        const val PLAYER = "player"
        const val CRUD = "crud"
        const val TWOPANE = "twopane"

        /**
         * The fragment needs to know if we're working in dualPane mode to handle
         * back navigation in a correct way. See [back] method
         *
         * @param player: The player we want to edit or add.
         * @param crud: To determine if we want to add or update a player.
         * @param isDualPane: Boolean to determine if the app is running in dual pane or not.
         */
        fun newInstance(player: IPlayer, crud: CRUDoperation, isDualPane: Boolean): PlayerAddEditFragment
        {
            val args = Bundle()

            //Serialize the passed values
            args.putSerializable(CRUD, crud as Serializable)
            args.putSerializable(PLAYER, player as Serializable)
            args.putSerializable(TWOPANE, isDualPane as Serializable)

            //Create the fragment and add the serialized data
            val fragment = PlayerAddEditFragment()
            fragment.arguments = args

            return fragment
        }

        //TODO: To be used for creating a new player instead of passing CRUDoperation value
        fun newInstance(): PlayerAddEditFragment
        {
            val args = Bundle()

            val fragment = PlayerAddEditFragment()
            fragment.arguments = args

            return fragment
        }
    }
}