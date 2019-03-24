package espresso.tests.login

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.twitter.R
import com.twitter.data.network.MockApi
import com.twitter.feature.login.LoginActivity
import espresso.helpers.ProgressBarHelper
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {

  @get:Rule
  var activityTestRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

  @Test
  fun verify_login_views_present() {
    onView(withId(R.id.input_email)).check(matches(isDisplayed()))
    onView(withId(R.id.input_password)).check(matches(isDisplayed()))
    onView(withId(R.id.button_login)).check(matches(isDisplayed()))
  }

  @Test
  fun login_with_empty_email_and_password_clicked() {
    onView(withId(R.id.button_login)).perform(click())
    onView(withText(R.string.login_error_empty_email_and_password))
        .inRoot(withDecorView(not(activityTestRule.activity.window.decorView)))
        .check(matches(isDisplayed()))
  }

  @Test
  fun login_with_empty_email_clicked() {
    onView(withId(R.id.input_password)).perform(typeText("any"))
    onView(withId(R.id.login_root)).perform(closeSoftKeyboard())
    onView(withId(R.id.button_login)).perform(click())
    onView(withText(R.string.login_error_empty_email))
        .inRoot(withDecorView(not(activityTestRule.activity.window.decorView)))
        .check(matches(isDisplayed()))
  }

  @Test
  fun login_with_empty_password_clicked() {
    onView(withId(R.id.input_email)).perform(typeText("any"))
    onView(withId(R.id.login_root)).perform(closeSoftKeyboard())
    onView(withId(R.id.button_login)).perform(click())
    onView(withText(R.string.login_error_empty_password))
        .inRoot(withDecorView(not(activityTestRule.activity.window.decorView)))
        .check(matches(isDisplayed()))
  }

  @Test
  fun login_with_existing_user_open_home() {
    onView(withId(R.id.input_email)).perform(typeText(MockApi.VALID_USER.email))
    onView(withId(R.id.input_password)).perform(typeText(MockApi.VALID_USER.password))
    onView(withId(R.id.login_root)).perform(closeSoftKeyboard())
    onView(withId(R.id.button_login)).perform(click())
    ProgressBarHelper.waitUntilProgressBarGone()
    onView(withId(R.id.home_root)).check(matches(isDisplayed()))
  }
}
