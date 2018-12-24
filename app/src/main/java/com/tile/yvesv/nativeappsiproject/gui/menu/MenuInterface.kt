package com.tile.yvesv.nativeappsiproject.gui.menu

/**
 * @interface [MenuInterface]: Used for consistency.
 * Activities implementing the menu should inherit this interface and override [menuStrategy].
 *
 * @property menuStrategy: The [MenuStrategy] to be initialized.
 *
 * @author Yves Vanduynslager
 */
interface MenuInterface
{
    val menuStrategy: MenuStrategy
}