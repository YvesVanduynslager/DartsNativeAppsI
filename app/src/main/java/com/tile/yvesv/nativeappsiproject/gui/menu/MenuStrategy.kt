package com.tile.yvesv.nativeappsiproject.gui.menu

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

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