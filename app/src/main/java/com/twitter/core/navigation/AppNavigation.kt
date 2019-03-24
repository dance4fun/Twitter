package com.twitter.core.navigation

import android.app.Activity
import android.content.Intent
import com.twitter.feature.home.HomeActivity
import com.twitter.feature.login.LoginActivity
import javax.inject.Inject

class AppNavigation @Inject constructor() : Navigation {

  override fun openScreen(context: Activity, screenType: Int) {
    when (screenType) {
      NavigationTypes.HOME -> startActivity(context, HomeActivity::class.java)
      NavigationTypes.LOGIN -> startActivity(context, LoginActivity::class.java)
      else -> throw RuntimeException("Unknown navigation type")
    }
  }

  private inline fun <reified T : Activity> startActivity(
      context: Activity, destination: Class<T>, initIntent: Intent.() -> Unit = { }
  ) {
    context.startActivity(Intent(context, destination).apply { initIntent() })
    context.finish()
  }
}
