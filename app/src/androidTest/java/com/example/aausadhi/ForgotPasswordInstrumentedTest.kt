package com.example.aausadhi

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.aausadhi.ui.activity.ForgotPasswordActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ForgotPasswordInstrumentedTest {
    @get:Rule
    val testRule = ActivityScenarioRule(ForgotPasswordActivity::class.java)

    @Test
    fun checkLogin() {
        onView(withId(R.id.emailInput)).perform(
            typeText("ram@gmail.com")
        )
        closeSoftKeyboard()

        // Wait for the submit button to be displayed and then click it
        onView(withId(R.id.sendEmailButton)).check(matches(isDisplayed())).perform(click())
        Thread.sleep(5000)

        // Wait for the resultText to be displayed and then check its text
        onView(withId(R.id.resultForgot)).check(matches(withText("OK")))
    }
}
