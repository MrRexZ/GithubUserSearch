package com.example.githubusersearch.utils

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.UiController
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher


object CustomViewActions {
    fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                //Ensure that only apply if it is a SearchView and if it is visible.
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun getDescription(): String {
                return "Change view text"
            }

            override fun perform(uiController: UiController, view: View) {
                val searchView = view as SearchView
                searchView.setQuery(text, true)

            }
        }
    }
}