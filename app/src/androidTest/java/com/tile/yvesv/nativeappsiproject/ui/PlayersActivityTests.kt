package com.tile.yvesv.nativeappsiproject.ui

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.tile.yvesv.nativeappsiproject.R
import com.tile.yvesv.nativeappsiproject.gui.activities.PlayersActivity
import com.tile.yvesv.nativeappsiproject.persistence.DartsDatabase
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PlayersActivityTests
{
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(PlayersActivity::class.java)

    @Before
    fun clearDatabase()
    {
        InstrumentationRegistry.getTargetContext()
                .deleteDatabase(DartsDatabase.DATABASE_NAME)
    }

    @Test
    fun createNewPlayer()
    {
        onView(withId(R.id.btn_add)).perform(click())
        /**
         * onView(withId(R.id.txt_name)) doesn't work here, since txt_name is ambiguous
         * over several layout files. We need to narrow the view down.
         * Here using the hint for the view.
         * @see [https://developer.android.com/reference/androidx/test/espresso/matcher/ViewMatchers]
         */
        onView(allOf(withId(R.id.txt_name), withHint("Name"))).perform(typeText("Yves"))
        //onView(withId(R.id.txt_name)).perform(typeText("Yves"))
        onView(allOf(withId(R.id.txt_description), withHint("Description"))).perform(typeText("Anything"))
        android.support.test.espresso.Espresso.closeSoftKeyboard()
        onView(withId(R.id.btn_save)).perform(click())
        //android.support.test.espresso.Espresso.pressBack()

        //CHECKS
        onView(withId(R.id.players_list_container)).check(matches((isDisplayed())))
        onView(withId(R.id.players_list_container)).check(matches(hasDescendant(withText("Yves"))))
        onView(withId(R.id.players_list_container)).check(matches(hasDescendant(withText("Anything"))))

        //onView(withId(R.id.edit_word)).perform(clearText())
    }

    @Test
    fun editPlayer()
    {
        onView(withId(R.id.player_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

        onView(allOf(withId(R.id.txt_name), withHint("Name"))).perform(clearText())
        onView(allOf(withId(R.id.txt_name), withHint("Name"))).perform(typeText("YvesEdited"))

        onView(allOf(withId(R.id.txt_description), withHint("Description"))).perform(clearText())
        onView(allOf(withId(R.id.txt_description), withHint("Description"))).perform(typeText("AnythingEdited"))

        android.support.test.espresso.Espresso.closeSoftKeyboard()
        onView(withId(R.id.btn_save)).perform(click())

        //CHECKS
        onView(withId(R.id.players_list_container)).check(matches((isDisplayed())))
        onView(withId(R.id.players_list_container)).check(matches(hasDescendant(withText("YvesEdited"))))
        onView(withId(R.id.players_list_container)).check(matches(hasDescendant(withText("AnythingEdited"))))
    }

    @Test
    fun deletePlayer()
    {
        onView(withId(R.id.player_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

        onView(withId(R.id.btn_delete)).perform(click())

        //CHECKS
        onView(withId(R.id.players_list_container)).check(matches((isDisplayed())))
    }

    @Test
    fun menuNavigation()
    {
        onView(withId(R.id.ranking)).perform(click())
        onView(withId(R.id.ranking_main)).check(matches((isDisplayed())))
        android.support.test.espresso.Espresso.pressBack()

        onView(withId(R.id.info)).perform(click())
        onView(withId(R.id.info_main)).check(matches((isDisplayed())))
        android.support.test.espresso.Espresso.pressBack()

        onView(withId(R.id.nogames)).perform(click())
        onView(withId(R.id.bored_main)).check(matches((isDisplayed())))
        android.support.test.espresso.Espresso.pressBack()

        onView(withId(R.id.players_main)).check(matches((isDisplayed())))
    }
}
