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
import com.tile.yvesv.nativeappsiproject.gui.activities.RankingActivity
import com.tile.yvesv.nativeappsiproject.persistence.DartsDatabase
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RankingActivityTests
{
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(RankingActivity::class.java)

    @Before
    fun clearDatabase()
    {
        InstrumentationRegistry.getTargetContext()
                .deleteDatabase(DartsDatabase.DATABASE_NAME)
    }

    @Test
    fun editScoreAndSave()
    {
        onView(withId(R.id.player_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

        onView(withId(R.id.plus_one)).perform(click())
        onView(withId(R.id.plus_one)).perform(click())
        onView(withId(R.id.plus_one)).perform(click())

        onView(withId(R.id.minus_one)).perform(click())
        onView(withId(R.id.minus_one)).perform(click())

        onView(withId(R.id.btn_save)).perform(click())

        //CHECKS
        onView(withId(R.id.ranking_main)).check(matches((isDisplayed())))
    }

    @Test
    fun editScoreAndCancel()
    {
        onView(withId(R.id.player_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

        onView(withId(R.id.plus_one)).perform(click())
        onView(withId(R.id.plus_one)).perform(click())
        onView(withId(R.id.plus_one)).perform(click())

        onView(withId(R.id.minus_one)).perform(click())
        onView(withId(R.id.minus_one)).perform(click())

        onView(withId(R.id.btn_cancel)).perform(click())

        //CHECKS
        onView(withId(R.id.player_detail_container)).check(matches((isDisplayed())))
        onView(withId(R.id.fragment_detail_container)).check(matches((isDisplayed())))
    }

    @Test
    fun menuNavigation()
    {
        onView(withId(R.id.players)).perform(click())
        onView(withId(R.id.players_main)).check(matches((isDisplayed())))
        android.support.test.espresso.Espresso.pressBack()

        onView(withId(R.id.info)).perform(click())
        onView(withId(R.id.info_main)).check(matches((isDisplayed())))
        android.support.test.espresso.Espresso.pressBack()

        onView(withId(R.id.nogames)).perform(click())
        onView(withId(R.id.bored_main)).check(matches((isDisplayed())))
        android.support.test.espresso.Espresso.pressBack()

        onView(withId(R.id.ranking_main)).check(matches((isDisplayed())))
    }
}
