package com.tile.yvesv.nativeappsiproject.gui.menu

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

/**
 * @interface [MenuStrategy]: Supertype for menu strategies.
 *
 * @property activityDescription: Description of the activity implementing the menu.
 *
 * @author Yves Vanduynslager
 */
interface MenuStrategy
{
    val activityDescription: String

    /**
     * Handles the menu selection
     * @param item The selected menu-item
     */
    fun menuSetup(act: AppCompatActivity, item: MenuItem) : Boolean
}