package com.example.githubusersearch.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.githubusersearch.R
import com.example.githubusersearch.utils.CustomViewActions.typeSearchViewText
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.githubusersearch.utils.IdlingResourceRule
import kotlinx.android.synthetic.main.activity_main.*


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val idlingResourceRule: IdlingResourceRule = IdlingResourceRule()

    @Test
    fun showWelcomeScreenOnlyInitially() {
        onView(withId(R.id.welcome_view)).check(matches(isDisplayed()))
        onView(withId(R.id.user_search_view_frame)).check(matches(isDisplayed()))
        onView(withId(R.id.empty_list_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.github_user_list_swipe_to_refresh)).check(matches(not(isDisplayed())))
    }


    @Test
    fun showSearchResultIfSearchSuccess() {
        attemptShowSuccessfulRes()
    }

    @Test
    fun emptySearchShowEmptyScreenResult() {
        attemptShowEmptySearchRes()
    }

    @Test
    fun showSearchFailScreenIfSearchFail() {
        attemptShowGithubApiErrorRes()
    }

    @Test
    fun canShowSuccessfulSearchResultAfterFailScreenShows() {
        attemptShowGithubApiErrorRes()
        attemptShowSuccessfulRes()
    }

    @Test
    fun canShowSuccessfulSearchResultAfterEmptyScreenShows() {
        attemptShowEmptySearchRes()
        attemptShowSuccessfulRes()
    }

    private fun attemptShowEmptySearchRes() {
        onView(withId(R.id.user_search_view)).perform(typeSearchViewText("asdsadadafgdsdfsfdsfdsfsfqweqeqeqwewqasdsadad"))
            .perform(click())
        onView(withId(R.id.empty_list_view)).check(matches(isDisplayed()))
    }

    private fun attemptShowGithubApiErrorRes() {
        onView(withId(R.id.user_search_view)).perform(typeSearchViewText("\\")).perform(click())
        onView(withId(R.id.error_view)).check(matches(isDisplayed()))
    }

    private fun attemptShowSuccessfulRes() {
        onView(withId(R.id.user_search_view)).perform(typeSearchViewText("anthony tjuatja")).perform(click())
        onView(withId(R.id.github_user_list_swipe_to_refresh)).check(matches(isDisplayed()))
        assert(rule.activity.github_users_list_rv.adapter!!.itemCount > 0)
    }


}