package com.drians.finalmoviecatalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.drians.finalmoviecatalogue.R
import com.drians.finalmoviecatalogue.utils.DataDummy
import com.drians.finalmoviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeActivityTest {

    private val dummyMovie = DataDummy.generateDummyMovies()
    private val dummyTv = DataDummy.generateDummyTvs()

    @get: Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size)
        )
    }

    @Test
    fun loadMovieDetail() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.text_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_vote_average)).check(matches(isDisplayed()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.image_header)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvs() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_tv)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTv.size)
        )
    }

    @Test
    fun loadDetailTv() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_tv)).perform(click())
        onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )
        onView(withId(R.id.text_name)).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.text_first_air_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_vote_average)).check(matches(isDisplayed()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.image_header)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteMovies() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.rv_favorite_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.text_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_vote_average)).check(matches(isDisplayed()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.image_header)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun loadFavoriteTvs() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_tv)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_favorite_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.text_name)).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.text_first_air_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_vote_average)).check(matches(isDisplayed()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.image_header)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }
}