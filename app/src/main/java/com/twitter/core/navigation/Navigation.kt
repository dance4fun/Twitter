package com.twitter.core.navigation

import android.app.Activity

interface Navigation {

  fun openScreen(context: Activity, @NavigationType screenType: Int)

}
