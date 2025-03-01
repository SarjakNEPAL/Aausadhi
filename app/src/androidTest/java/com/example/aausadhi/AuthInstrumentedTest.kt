package com.example.a35b_crud

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.aausadhi.R
import com.example.aausadhi.ui.activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AuthInstrumentedTest {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkLogin() {
        onView(withId(R.id.editEmailLogin)).perform(
            typeText("ram@gmail.com")
        )

        onView(withId(R.id.loginPasswordEntry)).perform(
            typeText("password")
        )

        closeSoftKeyboard()

        // Wait for the submit button to be displayed and then click it
        onView(withId(R.id.submitButton)).check(matches(isDisplayed())).perform(click())
        Thread.sleep(5000)

        // Wait for the resultText to be displayed and then check its text
        onView(withId(R.id.resultText)).check(matches(withText("Login failed")))
    }
}
